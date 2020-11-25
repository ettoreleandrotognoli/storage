package com.gitlab.ettoreleandrotognoli.storage;

import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(FileParameterResolver.class)
public class StorageFactoryImplTest {

    @Test
    public void createLocalStorageShouldReturnNonNull(File file) throws Exception {
        StorageFactoryImpl storageFactory = StorageFactoryImpl.getInstance();
        LocalStorage localStorage = storageFactory.createLocalStorage(file);
        assertThat(localStorage).isNotNull();
    }

    @Test
    public void createGitStorageShouldReturnNonNull(File file) throws Exception {
        StorageFactoryImpl storageFactory = StorageFactoryImpl.getInstance();
        GitStorage gitStorage = storageFactory.createGitStorage(file);
        assertThat(gitStorage).isNotNull();
    }

}
