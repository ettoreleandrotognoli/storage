package com.gitlab.ettoreleandrotognoli.storage.jackson;


import com.gitlab.ettoreleandrotognoli.storage.TestUtils;
import com.gitlab.ettoreleandrotognoli.storage.data.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ReadPersonTest {

    @Test
    public void testReadPerson() throws IOException {
        List<Person> people = (List<Person>) (Object) TestUtils.OBJECT_MAPPER.readerFor(Person.class).readValues(TestUtils.PERSON_FILE).readAll();
        Assertions.assertThat(people).hasSize(1);
        Person person = people.get(0);
        Assertions.assertThat(person.getName()).isEqualTo("Ettore");
        Assertions.assertThat(person.getAge()).isEqualTo(28);
    }

}
