package com.gitlab.ettoreleandrotognoli.storage.api;

public interface Storage {

    <E> Repository<E> repositoryFor(Class<E> type) throws Exception;
}
