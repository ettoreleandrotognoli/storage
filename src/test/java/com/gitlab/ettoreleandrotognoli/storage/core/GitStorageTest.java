package com.gitlab.ettoreleandrotognoli.storage.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.gitlab.ettoreleandrotognoli.storage.FakerParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.FileParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.GitStorageParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.ObjectMapperParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import com.gitlab.ettoreleandrotognoli.storage.core.git.strategies.AddCommitRebaseAndPush;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.URIish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({GitStorageParameterResolver.class, FakerParameterResolver.class, FileParameterResolver.class, ObjectMapperParameterResolver.class})
public class GitStorageTest {


    @Test
    void commit(GitStorage storage, Faker faker) throws Exception {
        String commitMessage = faker.lorem().paragraph();

        try (MutableStorageSession session = storage.openSession(commitMessage)) {
            session.mutableRepositoryFor(Person.class)
                    .store(Stream.of(new Person()));
        }
        Git git = storage.getGit();
        Status status = git
                .status()
                .call();
        assertThat(status.isClean())
                .isTrue();
        RevCommit lastCommit = git.log()
                .setMaxCount(1)
                .call()
                .iterator()
                .next();
        assertThat(lastCommit.getFullMessage())
                .isEqualTo(commitMessage);

    }

    @Test
    void push(
            File storageFile,
            File remoteFile,
            Faker faker,
            ObjectMapper objectMapper
    ) throws Exception {
        GitStorage localGitStorage = new GitStorage(new LocalStorage(storageFile, objectMapper), AddCommitRebaseAndPush::new);
        Git localGit = localGitStorage.getGit();
        Git remoteGit = Git.init().setDirectory(remoteFile).call();
        remoteGit.commit()
                .setAllowEmpty(true)
                .setMessage("initial commit")
                .call();
        localGitStorage.getGit()
                .remoteAdd()
                .setName(Constants.DEFAULT_REMOTE_NAME)
                .setUri(new URIish(remoteFile.toURI().toString()))
                .call();
        localGitStorage.getGit()
                .pull()
                .setRemote(Constants.DEFAULT_REMOTE_NAME)
                .setRemoteBranchName(Constants.MASTER)
                .call();
        remoteGit.commit()
                .setAllowEmpty(true)
                .setMessage("blank commit")
                .call();
        String commitMessage = faker.lorem().paragraph();
        try (MutableStorageSession session = localGitStorage.openSession(commitMessage)) {
            session.mutableRepositoryFor(Person.class)
                    .store(Stream.of(new Person()));
        }
        Status status = localGit
                .status()
                .call();
        assertThat(status.isClean())
                .isTrue();
        RevCommit localLastCommit = localGit.log()
                .setMaxCount(1)
                .call()
                .iterator()
                .next();
        assertThat(localLastCommit.getFullMessage())
                .isEqualTo(commitMessage);
        RevCommit remoteLastCommit = remoteGit.log()
                .setMaxCount(1)
                .call()
                .iterator()
                .next();
        assertThat(remoteLastCommit.getFullMessage())
                .isEqualTo(commitMessage);
    }

}
