package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.PersonDao;
import ru.kata.spring.boot_security.demo.model.Person;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.security.PersonDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Autowired
    public PersonServiceImpl(PersonDao personDao, @Lazy PasswordEncoder passwordEncoder, RoleService roleService) {
        this.personDao = personDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public List<Person> getAllPeople() {
        return personDao.getAllUsers();
    }

    @Override
    @Transactional
    public void addUser(Person person, Set<Integer> roleIds) {
        if (roleIds != null) {
            person.setRoles(roleService.getRolesByIds(roleIds));
        } else {
            Set<Integer> set = new HashSet<>();
            set.add(1);
            person.setRoles(roleService.getRolesByIds(set));
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.addUser(person);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        personDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds) {
        Person person = getUserById(id);
        if (person != null) {
            if (roleIds != null) {
                Set<Role> selectedRole = roleService.getRolesByIds(roleIds);
                person.setRoles(selectedRole);
            }
            if (!personDetails.getPassword().isEmpty()) {
                person.setPassword(passwordEncoder.encode(personDetails.getPassword()));
            }
        }
        personDao.editUserAndHisRoles(id, personDetails);
    }

    @Override
    public Person getUserById(int id) {
        return personDao.getUserById(id);
    }

    @Override
    public Optional<Person> getPersonByName(String username) {
        return personDao.getPersonByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personDao.getPersonByName(username).map(PersonDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public boolean isTableUsersEmpty() {
        return personDao.isTableUsersEmpty();
    }
}
