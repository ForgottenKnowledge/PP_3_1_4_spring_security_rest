package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void saveNewUser(User user, Set<Role> roles);

    List<User> getAllUsers();

    User getUser(long id);

    void updateUser(User userUpdate, Set<Role> roles);

    void deleteUser(long id);

    User getUser(String name);
}
