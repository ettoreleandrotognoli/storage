package com.gitlab.ettoreleandrotognoli.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;

import java.io.File;

public class StorageFactory {

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.findAndRegisterModules();
    }

    public static GitStorage createGitStorage(File path) throws Exception {
        return new GitStorage(createLocalStorage(path));
    }

    public static GitStorage createGitStorage(File path, ObjectMapper mapper) throws Exception {
        return new GitStorage(createLocalStorage(path, mapper));
    }

    public static LocalStorage createLocalStorage(File path) throws Exception {
        return new LocalStorage(path, OBJECT_MAPPER);
    }

    public static LocalStorage createLocalStorage(File path, ObjectMapper mapper) throws Exception {
        return new LocalStorage(path, mapper);
    }


}
