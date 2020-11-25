package com.gitlab.ettoreleandrotognoli.storage.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public interface StorageFactory {

    SessionSupportStorage createGitStorage(File path) throws Exception;

    SessionSupportStorage createGitStorage(File path, ObjectMapper mapper) throws Exception;

    MutableStorage createLocalStorage(File path) throws Exception;

    MutableStorage createLocalStorage(File path, ObjectMapper mapper) throws Exception;
}
