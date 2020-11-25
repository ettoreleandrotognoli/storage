package com.gitlab.ettoreleandrotognoli.storage;

import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.File;
import java.nio.file.Files;

public class GitStorageParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(GitStorage.class);
    }

    @Override
    public GitStorage resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            File file = Files.createTempDirectory("").toFile();
            file.mkdirs();
            LocalStorage localStorage = new LocalStorage(file, TestUtils.OBJECT_MAPPER);
            return new GitStorage(localStorage);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ParameterResolutionException(ex.getMessage());
        }

    }
}
