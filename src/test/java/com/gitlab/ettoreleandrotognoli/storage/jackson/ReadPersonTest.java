package com.gitlab.ettoreleandrotognoli.storage.jackson;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.ettoreleandrotognoli.storage.FakerParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.GitStorageParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.ObjectMapperParameterResolver;
import com.gitlab.ettoreleandrotognoli.storage.TestUtils;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

@ExtendWith({ObjectMapperParameterResolver.class})
public class ReadPersonTest {

    @Test
    public void testReadPerson(ObjectMapper objectMapper) throws IOException {
        List<Person> people = (List<Person>) (Object) objectMapper.readerFor(Person.class).readValues(TestUtils.PERSON_FILE).readAll();
        Assertions.assertThat(people).hasSize(1);
        Person person = people.get(0);
        Assertions.assertThat(person.getName()).isEqualTo("Ettore");
        Assertions.assertThat(person.getAge()).isEqualTo(28);
    }

}
