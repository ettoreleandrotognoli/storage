package com.gitlab.ettoreleandrotognoli.storage.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorage;

import javax.persistence.Entity;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LocalStorage implements MutableStorage {

    interface PathStrategy {
        File resolvePath(File basePath, Class<?> dataType) throws IOException;
    }

    private File basePath;
    private ObjectMapper objectMapper;
    private PathStrategy pathStrategy;

    static final PathStrategy DEFAULT_PATH_STRATEGY = new PathStrategy() {
        @Override
        public File resolvePath(File basePath, Class<?> dataType) throws IOException {
            Entity entity = dataType.getAnnotation(Entity.class);
            String pathName = entity != null ? entity.name() : dataType.getSimpleName();
            File repositoryPath = new File(basePath, pathName);
            repositoryPath.mkdirs();
            File dataFile = new File(repositoryPath, "data.yml");
            dataFile.createNewFile();
            return dataFile;
        }
    };

    public LocalStorage(File basePath, ObjectMapper objectMapper, PathStrategy pathStrategy) {
        this.basePath = basePath;
        this.objectMapper = objectMapper;
        this.pathStrategy = pathStrategy;
    }

    public LocalStorage(File basePath, ObjectMapper objectMapper) {
        this(basePath, objectMapper, DEFAULT_PATH_STRATEGY);
    }

    public File getBasePath() {
        return basePath;
    }

    @Override
    public <E> LocalRepository<E> repositoryFor(Class<E> type) throws Exception {
        File dataFile = pathStrategy.resolvePath(basePath, type);
        return new LocalRepository<>(
                objectMapper.readerFor(type),
                dataFile,
                Objects::hashCode
        );
    }

    @Override
    public <E> MutableLocalRepository<E> mutableRepositoryFor(Class<E> type) throws Exception {
        File dataFile = pathStrategy.resolvePath(basePath, type);
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
