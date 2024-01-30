package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    @NotEmpty(message = "имя не должно быть пустым")
    @Size(min = 2, max = 60, message = "имя должно быть от 2 до 60 символов")
    private String username;

    @NotEmpty(message = "фамилия не может быть пустой")
    @Size(min = 2, max = 60, message = "фамилия должна быть от 2 до 60 символов")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 0, message = "возраст не может быть отрицательным")
    @Column(name = "age")
    private int age;

    @Email
    @Column(name = "email")
    private String email;


    @Column(name = "password")
    private String password;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Person(String username, String lastName, int age, String email, String password, Set<Role> roles) {
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(username, person.username) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email) && Objects.equals(password, person.password);
    }

    public String getRolesString() {
        if (roles.toString().contains("ADMIN") && roles.toString().contains("USER")) {
            return "ADMIN USER";
        } else if (roles.toString().contains("USER")) {
            return "USER";
        } else {
            return "ADMIN";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, lastName, age, email, password);
    }
}
