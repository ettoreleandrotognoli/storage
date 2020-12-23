package com.gitlab.ettoreleandrotognoli.storage.core.git.strategies;

import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.core.AbstractLocalRepository;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;

public class AddCommitRebaseAndPush extends AddAndCommit implements MutableStorageSession, AbstractLocalRepository.Listener<Object> {


    public AddCommitRebaseAndPush(GitStorage gitStorage, String message) {
        super(gitStorage, message);
    }

    @Override
    public void close() throws Exception {
        try {
            commit();
            rebase();
            push();
        } catch (Exception exception) {
            rollback();
            throw exception;
        }
    }

    private void push() throws GitAPIException {
        gitStorage
                .getGit()
                .push()
                .setRemote(Constants.DEFAULT_REMOTE_NAME)
                .add(Constants.MASTER)
                .call();
    }

    private void rebase() throws GitAPIException {
        gitStorage
                .getGit()
                .pull()
                .setRemote(Constants.DEFAULT_REMOTE_NAME)
                .setRemoteBranchName(Constants.MASTER)
                .setRebase(true)
                .call();
    }

}
