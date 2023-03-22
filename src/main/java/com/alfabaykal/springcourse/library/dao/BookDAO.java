package com.alfabaykal.springcourse.library.dao;

import com.alfabaykal.springcourse.library.models.Book;
import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Books ORDER BY book_title", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Books WHERE book_id = ?",
                        new Object[]{bookId}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Books(book_title, author, publication_year) VALUES(?, ?, ?)",
                book.getBookTitle(), book.getAuthor(), book.getPublicationYear());
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE Books SET book_title = ?, author = ?, publication_year = ? WHERE book_id = ?",
                updatedBook.getBookTitle(), updatedBook.getAuthor(), updatedBook.getPublicationYear(), bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Books WHERE book_id = ?", bookId);
    }

    public Optional<Person> getPersonWhoIsReadingThisBook(int bookId) {
        return jdbcTemplate.query("SELECT Person.* FROM Person join Books on Person.person_id = Books.person_id WHERE book_id = ?",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void setPersonWhoIsReadingThisBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Books SET person_id = ? WHERE book_id = ?",
                personId, bookId);
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update("UPDATE Books SET person_id = NULL WHERE book_id = ?", bookId);
    }
}
