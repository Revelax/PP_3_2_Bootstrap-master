package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Person;
import ru.kata.spring.boot_security.demo.service.PersonService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String redirect = "redirect:/admin";
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final RoleService roleService;

    @Autowired
    public AdminController(PersonService personService, PersonValidator personValidator, RoleService roleService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.roleService = roleService;
    }
    @ModelAttribute("person")
    public Person getPerson() {
        return new Person();
    }

    @GetMapping("")
    public String adminPage(Model model, Principal principal) {
        Person person = personService.getPersonByName(principal.getName()).orElse(null);
        model.addAttribute("person", person);
        model.addAttribute("users", personService.getAllPeople());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("editUser", personService.getUserById(id));
        model.addAttribute("users", personService.getAllPeople());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }

    @PatchMapping("/edit/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("editUser") @Valid Person updatePerson, BindingResult bindingResult,
                         @RequestParam(value = "roles", required = false) Set<Integer> roleIds, Model model) {
        Person person = personService.getUserById(id);
        if (!person.getEmail().equals(updatePerson.getEmail())) {
            personValidator.validate(updatePerson, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "/admin";
        }
        if (bindingResult.hasErrors())
            return "/admin";

        personService.editUserAndHisRoles(id, updatePerson, roleIds);
        return redirect;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id,
                             @ModelAttribute("deleteUser") Person deleteUser,
                             Model model) {
        model.addAttribute("users", personService.getAllPeople());
        model.addAttribute("deleteUser", personService.getUserById(id));
        return "/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.deleteUserById(id);
        return redirect;
    }

    @GetMapping("/add")
    public String registrationPage(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("users", personService.getAllPeople());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }


    @PostMapping("/add")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                                      @RequestParam(value = "roles", required = false) Set<Integer> roleIds, Model model) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "/admin";
        }

        personService.addUser(person, roleIds);
        return redirect;
    }
}
