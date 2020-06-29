package com.rsynytskyi.tasktracker.service;

import com.rsynytskyi.tasktracker.controller.EnterController;
import com.rsynytskyi.tasktracker.dao.UserDao;
import com.rsynytskyi.tasktracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public User updateUser(int id, User updatedUser) {
        User forUpdateUser = userDao.findById(id);
        forUpdateUser.setFirstName(updatedUser.getFirstName());
        forUpdateUser.setLast_name(updatedUser.getLast_name());
        forUpdateUser.setEmail(updatedUser.getEmail());
        forUpdateUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return forUpdateUser;
    }

    @Override
    public void deleteUser(String id) {
        User user = userDao.findById(Integer.parseInt(id));
        userDao.delete(user);
    }
}
