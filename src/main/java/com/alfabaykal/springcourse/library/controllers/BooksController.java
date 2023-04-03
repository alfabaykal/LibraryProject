package com.alfabaykal.springcourse.library.controllers;

import com.alfabaykal.springcourse.library.services.BooksService;
import com.alfabaykal.springcourse.library.services.PeopleService;
import com.alfabaykal.springcourse.library.models.Book;
import com.alfabaykal.springcourse.library.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BooksService booksService;
    private PeopleService peopleService;

    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page"})
    public String index(Model model,
                        @RequestParam("page") int page,
                        @RequestParam("books_per_page") int booksPerPage) {
        model.addAttribute("books", booksService.findAll(page, booksPerPage));
        return "books/index";
    }

    @GetMapping(params = {"sort_by_year"})
    public String index(Model model, @RequestParam("sort_by_year") boolean sortByYear) {
        if(sortByYear)
            model.addAttribute("books", booksService.findByOrderByPublicationYear());
        else model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page", "sort_by_year"})
    public String index(Model model,
                        @RequestParam("page") int page,
                        @RequestParam("books_per_page") int booksPerPage,
                        @RequestParam("sort_by_year") boolean sortByYear) {
        if (sortByYear)
        model.addAttribute("books", booksService.findByOrderByPublicationYear(page, booksPerPage));
        else model.addAttribute("books", booksService.findAll(page, booksPerPage));
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model) {
        model.addAttribute("book", booksService.findOne(bookId));

        Optional<Person> personWhoIsReadingThisBook = booksService.getPersonWhoIsReadingThisBook(bookId);

        if(personWhoIsReadingThisBook != null)
        model.addAttribute("person", booksService.getPersonWhoIsReadingThisBook(bookId).get());
        else {
            model.addAttribute("person", new Person());
            model.addAttribute("people", peopleService.findAll());
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

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String edit(@PathVariable("bookId") int bookId, Model model) {
        model.addAttribute(booksService.findOne(bookId));
        return "books/edit";
    }

    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("bookId") int bookId) {
        if(bindingResult.hasErrors())
            return "books/edit";

        booksService.update(bookId, book);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        booksService.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/set_person/{bookId}")
    public String setPersonWhoIsReadingThisBook(@PathVariable ("bookId") int bookId, int personId) {
        booksService.setPersonWhoIsReadingThisBook(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/release/{bookId}")
    public String releaseBook(@PathVariable("bookId") int bookId) {
        booksService.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("book", new Book());
        return "books/search";
    }

    @GetMapping(value = "/search", params = {"bookTitle"})
    public String search(Model model, @RequestParam("bookTitle") String bookTitle) {
        model.addAttribute("book", new Book());
        List<String> people = new ArrayList<>();
        List<Book> books = booksService.findByBookTitleStartsWith(bookTitle);
        if (!books.isEmpty()) {
            for (Book book :
                    books) {
                if (book.getPersonId() != null)
                    people.add(booksService.getPersonWhoIsReadingThisBook(book).get().getFullName());
                else people.add(null);
            }
            model.addAttribute("books", books);
            model.addAttribute("people", people);
        }

        return "books/search";
    }
}
