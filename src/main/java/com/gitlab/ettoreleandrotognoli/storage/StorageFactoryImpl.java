package com.gitlab.ettoreleandrotognoli.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gitlab.ettoreleandrotognoli.storage.api.StorageFactory;
import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;

import java.io.File;

public class StorageFactoryImpl implements StorageFactory {

    private static class LazyHolder {
        static final StorageFactoryImpl INSTANCE = new StorageFactoryImpl();
    }

    public static StorageFactoryImpl getInstance() {
        return LazyHolder.INSTANCE;
    }

    private ObjectMapper objectMapper;

    public StorageFactoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected StorageFactoryImpl() {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.findAndRegisterModules();
    }

    @Override
    public GitStorage createGitStorage(File path) throws Exception {
        return new GitStorage(createLocalStorage(path));
    }

    @Override
    public GitStorage createGitStorage(File path, ObjectMapper mapper) throws Exception {
        return new GitStorage(createLocalStorage(path, mapper));
    }

    @Override
    public LocalStorage createLocalStorage(File path) throws Exception {
        return new LocalStorage(path, objectMapper);
    }

    @Override
    public LocalStorage createLocalStorage(File path, ObjectMapper mapper) throws Exception {
        return new LocalStorage(path, mapper);
    }


}
