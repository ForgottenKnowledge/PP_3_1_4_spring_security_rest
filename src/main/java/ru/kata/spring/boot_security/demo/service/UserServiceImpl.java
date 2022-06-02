package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;


    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Override
    @Transactional
    public void saveNewUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User userUpdate, Set<Role> roles) {
        String password = getUserById(userUpdate.getId()).getPassword();
        if (userUpdate.getPassword().equals(password)) {
            userUpdate.setPassword(password);
        } else {
            userUpdate.setPassword(bCryptPasswordEncoder.encode(userUpdate.getPassword()));
        }
        userUpdate.setRoles(roles);
        userDao.updateUser(userUpdate);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }

}
