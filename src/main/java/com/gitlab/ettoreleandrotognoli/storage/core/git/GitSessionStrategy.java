package com.gitlab.ettoreleandrotognoli.storage.core.git;

import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;

public interface GitSessionStrategy {

    MutableStorageSession openSession(GitStorage gitStorage, String message);

}
