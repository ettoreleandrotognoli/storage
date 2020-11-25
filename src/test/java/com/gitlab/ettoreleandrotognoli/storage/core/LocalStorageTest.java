package com.gitlab.ettoreleandrotognoli.storage.core;

import com.gitlab.ettoreleandrotognoli.storage.FileParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.TestUtils;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;

@ExtendWith(FileParameterResolver.class)
public class LocalStorageTest {

    @Test
    public void repositoryFor(File path) throws Exception {
        LocalStorage storage = new LocalStorage(
                path, TestUtils.OBJECT_MAPPER
        );
        storage.repositoryFor(Person.class);
    }

    @Test
    public void mutableRepositoryFor(File path) throws Exception {
        LocalStorage storage = new LocalStorage(
                path, TestUtils.OBJECT_MAPPER
        );
        storage.mutableRepositoryFor(Person.class);
    }


}
