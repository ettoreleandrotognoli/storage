package com.gitlab.ettoreleandrotognoli.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ObjectMapperParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        return parameterContext.getParameter().getType().isAssignableFrom(ObjectMapper.class);
    }

    @Override
    public ObjectMapper resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return TestUtils.OBJECT_MAPPER;
    }
}
