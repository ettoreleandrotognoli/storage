package com.gitlab.ettoreleandrotognoli.storage.api;

import java.util.stream.Stream;

public interface Repository<E> {

    Stream<E> streamAll() throws Exception;

}
