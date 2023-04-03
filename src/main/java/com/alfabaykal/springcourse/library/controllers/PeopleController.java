package com.alfabaykal.springcourse.library.controllers;

import com.alfabaykal.springcourse.library.models.Person;
import com.alfabaykal.springcourse.library.services.PeopleService;
import com.alfabaykal.springcourse.library.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PeopleService peopleService;
    private PersonValidator personValidator;

    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{personId}")
    public String show(@PathVariable("personId") int personId, Model model) {
        model.addAttribute("person", peopleService.findOne(personId));

        if(!peopleService.getPersonBooks(personId).isEmpty())
        model.addAttribute("books", peopleService.getPersonBooks(personId));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(@PathVariable("personId") int personId, Model model) {
        model.addAttribute(peopleService.findOne(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("personId") int personId) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String delete(@PathVariable("personId") int personId) {
        peopleService.delete(personId);
        return "redirect:/people";
    }
}
