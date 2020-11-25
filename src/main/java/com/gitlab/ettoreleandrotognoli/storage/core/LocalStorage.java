package com.gitlab.ettoreleandrotognoli.storage.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorage;

import java.io.File;
import java.util.Objects;

public class LocalStorage implements MutableStorage {

    private File basePath;
    private ObjectMapper objectMapper;

    public LocalStorage(File basePath, ObjectMapper objectMapper) {
        this.basePath = basePath;
        this.objectMapper = objectMapper;
    }

    public File getBasePath() {
        return basePath;
    }

    @Override
    public <E> LocalRepository<E> repositoryFor(Class<E> type) throws Exception {
        String pathName = type.getCanonicalName();
        File repositoryPath = new File(basePath, pathName);
        repositoryPath.mkdirs();
        File dataFile = new File(repositoryPath, "data.yml");
        dataFile.createNewFile();
        return new LocalRepository<>(
                objectMapper.readerFor(type),
                dataFile,
                Objects::hashCode
        );
    }

    @Override
    public <E> MutableLocalRepository<E> mutableRepositoryFor(Class<E> type) throws Exception {
        String pathName = type.getCanonicalName();
        File repositoryPath = new File(basePath, pathName);
        repositoryPath.mkdirs();
        File dataFile = new File(repositoryPath, "data.yml");
        dataFile.createNewFile();
        return new MutableLocalRepository<E>(
                objectMapper.writerFor(type),
                objectMapper.readerFor(type),
                dataFile,
                Objects::hashCode
        );
    }

    @Override
    public String toString() {
        return "LocalStorage{" +
                "basePath=" + basePath +
                '}';
    }
}
