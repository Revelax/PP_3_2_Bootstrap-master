package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Person;
import ru.kata.spring.boot_security.demo.service.PersonService;
import ru.kata.spring.boot_security.demo.service.PersonServiceImpl;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;


@Component
public class InitDataUtil {

    private final PersonService personService;

    @Autowired
    public InitDataUtil(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {

        if (personService.isTableUsersEmpty()) {
            Person person1 = new Person();
            person1.setUsername("admin");
            person1.setLastName("admin");
            person1.setAge(30);
            person1.setEmail("admin@mail.ru");
            person1.setPassword("admin");

            Person person2 = new Person();
            person2.setUsername("user");
            person2.setLastName("user");
            person2.setAge(30);
            person2.setEmail("user@mail.ru");
            person2.setPassword("user");

            Set<Integer> adminR = Collections.singleton(2);
            Set<Integer> userR = Collections.singleton(1);

            personService.addUser(person1, adminR);
            personService.addUser(person2, userR);

        }
    }
}


