package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    List<Person> getAllUsers();

    void addUser(Person person);

    void editUserAndHisRoles(int id, Person personDetails);

    void deleteUserById(int id);

    Person getUserById(int id);

    boolean isTableUsersEmpty();

    Optional<Person> getPersonByName(String username);
}
