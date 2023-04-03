package com.alfabaykal.springcourse.library.services;

import com.alfabaykal.springcourse.library.models.Book;
import com.alfabaykal.springcourse.library.models.Person;
import com.alfabaykal.springcourse.library.repositories.BooksRepository;
import com.alfabaykal.springcourse.library.repositories.PeopleRepository;
import java.util.Collections;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findByOrderByPublicationYear() {
        return booksRepository.findByOrderByPublicationYear();
    }

    public List<Book> findByOrderByPublicationYear(int page, int booksPerPage) {
        return booksRepository.findByOrderByPublicationYear(PageRequest.of(page, booksPerPage));
    }

    public List<Book> findByBookTitleStartsWith(String bookTitle) {
        if(bookTitle.isEmpty())
            return Collections.emptyList();

        return booksRepository.findByBookTitleStartsWith(bookTitle);
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Optional<Person> getPersonWhoIsReadingThisBook(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        if(book.getPersonId() == null) {
            return null;
        } else {
        return peopleRepository.findById(book.getPersonId());
        }
    }

    public Optional<Person> getPersonWhoIsReadingThisBook(Book book) {
        if(book.getPersonId() == null) {
            return null;
        } else {
            return peopleRepository.findById(book.getPersonId());
        }
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBok) {
        updatedBok.setPersonId(id);
        booksRepository.save(updatedBok);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    @Transactional
    public void setPersonWhoIsReadingThisBook(int bookId, int personId) {
        Book updatedBook = booksRepository.getOne(bookId);
        updatedBook.setPersonId(personId);
        updatedBook.setWasTaken(new Date());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void releaseBook(int bookId) {
        Book updatedBook = booksRepository.getOne(bookId);
        updatedBook.setPersonId(null);
        updatedBook.setWasTaken(null);
        booksRepository.save(updatedBook);
    }
}
