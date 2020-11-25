package com.gitlab.ettoreleandrotognoli.storage;

import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.nio.file.Files;

public class LocalStorageParametersResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(LocalStorage.class);
    }

    @Override
    public LocalStorage resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            return new LocalStorage(Files.createTempDirectory("").toFile(), TestUtils.OBJECT_MAPPER);
        } catch (IOException exception) {
            throw new ParameterResolutionException(exception.getMessage());
        }
    }
}
