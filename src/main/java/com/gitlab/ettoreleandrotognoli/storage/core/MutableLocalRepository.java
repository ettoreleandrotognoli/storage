package com.gitlab.ettoreleandrotognoli.storage.core;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableRepository;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MutableLocalRepository<E> extends LocalRepository<E> implements MutableRepository<E> {

    private ObjectWriter objectWriter;

    public MutableLocalRepository(ObjectWriter objectWriter, ObjectReader objectReader, File dataFile, Function<E, Object> extractPk) {
        super(objectReader, dataFile, extractPk);
        this.objectWriter = objectWriter;
    }


    @Override
    public MutableLocalRepository<E> store(Stream<E> bunch) throws Exception {
        Map<Object, List<E>> newDataGroupedByPk = bunch
                .collect(Collectors.groupingBy(extractPk));
        for (Map.Entry<Object, List<E>> entry : newDataGroupedByPk.entrySet()) {
            if (entry.getValue().size() > 1) {
                throw new Exception(String.format("Multiple objects with same pk (%s) : %s", entry.getKey(), entry.getValue()));
            }
        }
        Map<Object, List<E>> oldDataGroupedByPk = streamAll()
                .collect(Collectors.groupingBy(extractPk));

        newDataGroupedByPk.entrySet()
                .stream()
                .forEach(entry -> oldDataGroupedByPk.put(entry.getKey(), entry.getValue()));

        Stream<E> finalData = oldDataGroupedByPk.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(l -> l.get(0));
        writeData(finalData);
        return this;
    }

    protected void writeData(Stream<E> finalData) throws Exception {
        SequenceWriter sequenceWriter = objectWriter.writeValues(dataFile);

        finalData.forEach(it -> {
            try {
                sequenceWriter.write(it);
            } catch (Exception ex) {
                throw new RuntimeException(String.format("Error writing the object %s", it), ex);
            }
        });
        sequenceWriter.flush();
        sequenceWriter.close();
        this.fireDataChanged(Stream.of(this.dataFile));
    }

    @Override
    public MutableRepository<E> remove(Predicate<E> predicate) throws Exception {
        Stream<E> keepIt = streamAll()
                .filter(predicate.negate());
        writeData(keepIt);
        return this;
    }

    @Override
    public String toString() {
        return "MutableLocalRepository{" +
                "dataFile=" + dataFile +
                '}';
    }
}
