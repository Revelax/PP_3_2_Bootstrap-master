package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonService extends UserDetailsService {
    List<Person> getAllPeople();

    void addUser(Person person, Set<Integer> roleIds);

    void deleteUserById(int id);

    void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds);

    Person getUserById(int id);

    Optional<Person> getPersonByName(String username);

    boolean isTableUsersEmpty();

}
