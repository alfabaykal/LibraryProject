package com.alfabaykal.springcourse.library.dao;

import com.alfabaykal.springcourse.library.models.Book;
import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY full_name", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name = ?",
                        new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny() ;
    }

    public Person show(int personId) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?",
                        new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES(?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_birth = ? WHERE person_id = ?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
    }

    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", personId);
    }

    public List<Book> getPersonBooks(int personId) {
        return jdbcTemplate.query("SELECT book_title, author, publication_year FROM Books WHERE person_id = ? ORDER BY book_title",
                new BeanPropertyRowMapper<>(Book.class), personId);
    }

}
