package com.gitlab.ettoreleandrotognoli.storage.api;

public interface SessionSupportStorage extends Storage {

    MutableStorageSession openSession();

    MutableStorageSession openSession(String message);
}
