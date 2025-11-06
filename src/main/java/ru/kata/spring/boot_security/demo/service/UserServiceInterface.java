package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Set;

public interface UserServiceInterface {


    User saveUser(String username, String rawPassword, Set<Role> roles);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);

    User findByUsername(String name);

    User updateUser(Long id, String username, String rawPassword, Set<Role> roles);
}
