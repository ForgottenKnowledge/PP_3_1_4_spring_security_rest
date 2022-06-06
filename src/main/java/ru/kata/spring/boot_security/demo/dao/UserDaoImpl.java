package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

//    private final PasswordEncoder passwordEncoder;
//
//    public UserDaoImpl(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(getUser(id));
    }

    @Override
    public User getUser(String name) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
