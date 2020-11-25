package com.gitlab.ettoreleandrotognoli.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

public class TestUtils {

    public static final File PERSON_FILE;

    public static final ObjectMapper OBJECT_MAPPER;


    static {
        OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.findAndRegisterModules();
        PERSON_FILE = new File(TestUtils.class.getClassLoader().getResource("person.yml").getFile());
    }


}
