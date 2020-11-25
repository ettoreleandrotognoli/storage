package com.gitlab.ettoreleandrotognoli.storage.core.git;

import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.api.Repository;
import com.gitlab.ettoreleandrotognoli.storage.api.SessionSupportStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.LocalStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.strategies.AlwaysAdd;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;

import java.io.File;

public class GitStorage implements SessionSupportStorage {

    private Git git;
    private GitSessionStrategy sessionStrategy;
    private LocalStorage localStorage;


    public GitStorage(LocalStorage localStorage) throws Exception {
        this(localStorage, AlwaysAdd::new);
    }

    public GitStorage(LocalStorage localStorage, GitSessionStrategy sessionStrategy) throws Exception {
        this.localStorage = localStorage;
        this.sessionStrategy = sessionStrategy;
        File gitDir = new File(localStorage.getBasePath(), Constants.DOT_GIT);
        this.git = gitDir.isDirectory() ? Git.open(gitDir) : Git.init().setDirectory(localStorage.getBasePath()).call();
    }

    @Override
    public <E> Repository<E> repositoryFor(Class<E> type) throws Exception {
        return localStorage.repositoryFor(type);
    }

    @Override
    public MutableStorageSession openSession() {
        return openSession("");
    }

    public MutableStorageSession openSession(String message) {
        return sessionStrategy.openSession(this, message);
    }

    public Git getGit() {
        return git;
    }

    public LocalStorage getLocalStorage() {
        return localStorage;
    }
}
