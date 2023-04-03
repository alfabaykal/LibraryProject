package com.alfabaykal.springcourse.library.repositories;

import com.alfabaykal.springcourse.library.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByPersonId(int personId);
    List<Book> findByOrderByPublicationYear();
    List<Book> findByOrderByPublicationYear(Pageable pageable);
    List<Book> findByBookTitleStartsWith(String bookTitle);
}
