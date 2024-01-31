package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDaoImpl implements PersonDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isTableUsersEmpty() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Person");
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    @Override
    public List<Person> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM Person u", Person.class).getResultList();
    }

    @Override
    public void addUser(Person person) {
        entityManager.persist(person);
    }

    @Override
    public void editUserAndHisRoles(int id, Person personDetails) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            person.setUsername(personDetails.getUsername());
            person.setLastName(personDetails.getLastName());
            person.setAge(personDetails.getAge());
            person.setEmail(personDetails.getEmail());
        } else {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
    }

    @Override
    public void deleteUserById(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person == null) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
        entityManager.remove(person);
    }

    @Override
    public Optional<Person> getPersonByName(String email) {
        String query = "SELECT p FROM Person p JOIN FETCH p.roles WHERE p.email = :email";
        try {
            Person person = entityManager.createQuery(query, Person.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(person);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Person getUserById(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person == null) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
        return person;
    }
}
