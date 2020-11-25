package com.gitlab.ettoreleandrotognoli.storage.core;

import com.fasterxml.jackson.databind.ObjectReader;
import com.gitlab.ettoreleandrotognoli.storage.api.Repository;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LocalRepository<E> extends AbstractLocalRepository<E> implements Repository<E> {


    protected ObjectReader objectReader;
    protected File dataFile;
    protected Function<E, Object> extractPk;


    public LocalRepository(ObjectReader objectReader, File dataFile, Function<E, Object> extractPk) {
        this.objectReader = objectReader;
        this.dataFile = dataFile;
        this.extractPk = extractPk;
    }


    @Override
    public Stream<E> listAll() throws Exception {
        Iterable<E> iterable = () -> {
            try {
                return objectReader.readValues(dataFile);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        };
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Override
    public String toString() {
        return "LocalRepository{" +
                "dataFile=" + dataFile +
                '}';
    }
}
