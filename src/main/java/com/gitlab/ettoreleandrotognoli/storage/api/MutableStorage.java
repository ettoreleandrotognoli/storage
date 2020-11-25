package com.gitlab.ettoreleandrotognoli.storage.api;

public interface MutableStorage extends Storage {

    <E> MutableRepository<E> mutableRepositoryFor(Class<E> type) throws Exception;

}
