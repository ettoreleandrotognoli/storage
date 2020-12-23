package com.gitlab.ettoreleandrotognoli.storage.core.git.strategies;

import com.gitlab.ettoreleandrotognoli.storage.api.MutableRepository;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.api.Repository;
import com.gitlab.ettoreleandrotognoli.storage.core.AbstractLocalRepository;
import com.gitlab.ettoreleandrotognoli.storage.core.MutableLocalRepository;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.lib.Constants;

public class AddAndCommit implements MutableStorageSession, AbstractLocalRepository.Listener<Object> {

    protected final GitStorage gitStorage;
    protected final String message;

    public AddAndCommit(GitStorage gitStorage, String message) {
        this.gitStorage = gitStorage;
        this.message = message;
    }

    @Override
    public <E> MutableRepository<E> mutableRepositoryFor(Class<E> type) throws Exception {
        MutableLocalRepository<E> repository = gitStorage.getLocalStorage().mutableRepositoryFor(type);
        repository.addListener((AbstractLocalRepository.Listener<E>) this);
        return repository;
    }

    @Override
    public <E> Repository<E> repositoryFor(Class<E> type) throws Exception {
        return gitStorage.getLocalStorage().repositoryFor(type);
    }

    @Override
    public void close() throws Exception {
        try {
            commit();
        } catch (Exception exception) {
            rollback();
            throw exception;
        }
    }

    public String commit() throws Exception {
        return gitStorage.getGit()
                .commit()
                .setMessage(message)
                .call()
                .getFullMessage();
    }

    public void rollback() throws Exception {
        gitStorage.getGit().checkout().addPath(".").call();
    }

    @Override
    public void dataChanged(AbstractLocalRepository.Event<Object> event) throws Exception {
        String pathPrefix = gitStorage.getLocalStorage().getBasePath().getAbsolutePath();
        AddCommand add = gitStorage.getGit().add();
        event.getChangedFiles()
                .map(file -> file.getAbsolutePath().replaceFirst(pathPrefix, "").substring(1))
                .forEach(fileName -> add.addFilepattern(fileName));
        add.call();
    }
}
