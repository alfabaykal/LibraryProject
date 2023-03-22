package com.alfabaykal.springcourse.library.controllers;

import com.alfabaykal.springcourse.library.dao.BookDAO;
import com.alfabaykal.springcourse.library.dao.PersonDAO;
import com.alfabaykal.springcourse.library.models.Book;
import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookDAO bookDAO;
    private PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));

        Optional<Person> personWhoIsReadingThisBook = bookDAO.getPersonWhoIsReadingThisBook(bookId);

        if(personWhoIsReadingThisBook.isPresent())
        model.addAttribute("person", bookDAO.getPersonWhoIsReadingThisBook(bookId).get());
        else {
            model.addAttribute("person", new Person());
            model.addAttribute("people", personDAO.index());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String edit(@PathVariable("bookId") int bookId, Model model) {
        model.addAttribute(bookDAO.show(bookId));
        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("bookId") int bookId) {
        if(bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(bookId, book);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/set_person/{bookId}")
    public String setPersonWhoIsReadingThisBook(@PathVariable ("bookId") int bookId, int personId) {
        bookDAO.setPersonWhoIsReadingThisBook(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/release/{bookId}")
    public String releaseBook(@PathVariable("bookId") int bookId) {
        bookDAO.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }
}
