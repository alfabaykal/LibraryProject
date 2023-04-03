package com.alfabaykal.springcourse.library.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "Books")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "book_title")
    @NotEmpty(message = "Введите название")
    private String bookTitle;

    @Column(name = "author")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){2,}", message = "Введите имя и фамилию автора в формате: Имя Фамилия")
    private String author;

    @Column(name = "publication_year")
    @Max(value = 2024, message = "Год должен быть меньше 2024")
    private int publicationYear;

    @Column(name = "was_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wasTaken;

    @Transient
    private boolean overdue;

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

    public Date getWasTaken() {
        return wasTaken;
    }

    public void setWasTaken(Date wasTaken) {
        this.wasTaken = wasTaken;
    }

    public boolean isOverdue() {
        return new Date().getTime() - this.getWasTaken().getTime() >= 864000000;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
