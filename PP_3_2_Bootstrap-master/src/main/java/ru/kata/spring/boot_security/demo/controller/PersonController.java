package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Person;
import ru.kata.spring.boot_security.demo.service.PersonService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@Controller
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/user")
    public String sayHello(Model model, Principal principal) {
        Person person = personService.getPersonByName(principal.getName()).orElseThrow(EntityNotFoundException::new);

        model.addAttribute("person", person);
        return "/index";
    }

}
