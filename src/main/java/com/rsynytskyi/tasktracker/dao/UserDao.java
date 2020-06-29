package com.rsynytskyi.tasktracker.dao;

import com.rsynytskyi.tasktracker.model.User;

import java.util.List;

public interface UserDao {

    User findById(int id);

    List<User> findAll();

    void save(User user);

    void delete(User user);
}
