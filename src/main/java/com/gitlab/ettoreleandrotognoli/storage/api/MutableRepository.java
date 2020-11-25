package com.gitlab.ettoreleandrotognoli.storage.api;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableRepository<E> extends Repository<E> {

    MutableRepository<E> store(Stream<E> bunch) throws Exception;

    MutableRepository<E> remove(Predicate<E> predicate) throws Exception;
}
