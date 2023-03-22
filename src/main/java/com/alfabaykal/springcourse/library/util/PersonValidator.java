package com.alfabaykal.springcourse.library.util;

import com.alfabaykal.springcourse.library.dao.PersonDAO;
import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator  implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.show(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Данный читатель уже добавлен");
        }
    }
}
