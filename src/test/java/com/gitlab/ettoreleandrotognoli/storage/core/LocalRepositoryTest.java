package com.gitlab.ettoreleandrotognoli.storage.core;

import com.gitlab.ettoreleandrotognoli.storage.LocalStorageParametersResolver;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@ExtendWith(LocalStorageParametersResolver.class)
public class LocalRepositoryTest {

    @Test
    public void readEmpty(LocalStorage storage) throws Exception {
        int size = storage.repositoryFor(Person.class).streamAll().collect(Collectors.toList()).size();
        Assertions.assertThat(size).isZero();
    }

    @Test
    public void writeStuff(LocalStorage storage) throws Exception {
        storage.mutableRepositoryFor(Person.class).store(Stream.of(new Person()));
    }

    @Test
    public void readWithOne(LocalStorage storage) throws Exception {
        storage.mutableRepositoryFor(Person.class).store(Stream.of(new Person()));
        int size = storage.repositoryFor(Person.class).streamAll().collect(Collectors.toList()).size();
        Assertions.assertThat(size).isEqualTo(1);
    }

    @Test
    public void writeSameStuff(LocalStorage storage) {
        Assertions.catchThrowable(() -> {
            storage.mutableRepositoryFor(Person.class)
                    .store(Stream.of(new Person(null), new Person(null)));
        });
    }

    @Test
    public void writeManyStuff(LocalStorage storage) throws Exception {
        storage.mutableRepositoryFor(Person.class).store(Stream.of(
                new Person(),
                new Person(),
                new Person(),
                new Person(),
                new Person()
        ));
    }
}
