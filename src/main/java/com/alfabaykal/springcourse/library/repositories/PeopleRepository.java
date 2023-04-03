package com.alfabaykal.springcourse.library.repositories;

import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findPersonByFullName(String fullName);
}
