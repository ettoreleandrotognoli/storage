package com.gitlab.ettoreleandrotognoli.storage.core;

import com.github.javafaker.Faker;
import com.gitlab.ettoreleandrotognoli.storage.FakerParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.GitStorageParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.api.MutableStorageSession;
import com.gitlab.ettoreleandrotognoli.storage.core.git.GitStorage;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({GitStorageParameterResolver.class, FakerParameterResolver.class})
public class GitStorageTest {


    @Test
    void commit(GitStorage storage, Faker faker) throws Exception {
        String commitMessage = faker.lorem().paragraph();

        try (MutableStorageSession session = storage.openSession(commitMessage)) {
            session.mutableRepositoryFor(Person.class)
                    .store(Stream.of(new Person()));
        }
        Git git = Git.open(storage.getLocalStorage().getBasePath());
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

}
