package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUser(Long id);
    void updateUser(User user, Set<Role> roles);
    void deleteUser(Long id);
    User getUser(String name);
}
