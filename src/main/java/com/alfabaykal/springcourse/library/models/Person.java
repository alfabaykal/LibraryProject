package com.alfabaykal.springcourse.library.models;

import javax.validation.constraints.*;

public class Person {
    private int personId;

    @NotEmpty(message = "Поле ФИО не должно быть пустым")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3,}", message = "Пожалуйста, введите полные ФИО")
    private String fullName;

    @Min(value = 1800, message = "Год рождения должен быть больше чем 1800")
    @Max(value = 2024, message = "Год рождения должен быть меньше 2024")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personID) {
        this.personId = personID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int age) {
        this.yearOfBirth = age;
    }

}
