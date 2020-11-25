package com.gitlab.ettoreleandrotognoli.storage.data;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class Person {

    private UUID id;
    private String name;
    private int age;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Person(UUID id) {
        this.id = id;
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person() {
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now();
        this.createdAt = ZonedDateTime.now();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
