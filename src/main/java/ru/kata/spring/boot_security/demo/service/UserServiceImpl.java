package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;


    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Override
    @Transactional
    public void saveNewUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User userUpdate, Set<Role> roles) {
//        String password = getUser(userUpdate.getId()).getPassword();
//        if (userUpdate.getPassword().equals(password)) {
//            userUpdate.setPassword(password);
//        } else {
//            userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
//        }
//        userUpdate.setRoles(roles);
//        userDao.updateUser(userUpdate, roles);
        if (!userUpdate.getPassword().equals(getUser(userUpdate.getId()).getPassword())){
            userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        userUpdate.setRoles(roles);
        userDao.updateUser(userUpdate, roles);
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

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetails user = userDao.getUser(name);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with that name");
        }
        return user;
    }
}
