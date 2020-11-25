package com.gitlab.ettoreleandrotognoli.storage.core;

import com.gitlab.ettoreleandrotognoli.storage.GitStorageParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.Stream;

@ExtendWith(GitStorageParameterResolver.class)
public class GitStorageTest {


    @Test
    void commit(GitStorage storage) throws Exception {

        try (MutableStorageSession session = storage.openSession("fuu")) {
            session.mutableRepositoryFor(Person.class)
                    .store(Stream.of(new Person()));
        }

    }

}
