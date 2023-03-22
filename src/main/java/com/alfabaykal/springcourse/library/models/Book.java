package com.alfabaykal.springcourse.library.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Book {
    private int bookId;

    private Integer personId;

    @NotEmpty(message = "Введите название")
    private String bookTitle;

    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){2,}", message = "Введите имя и фамилию автора в формате: Имя Фамилия")
    private String author;

    @Max(value = 2024, message = "Год должен быть меньше 2024")
    private int publicationYear;

    public Book() {
    }

    public Book(String bookTitle, String author, int publicationYear) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
