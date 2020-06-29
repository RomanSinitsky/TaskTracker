package com.rsynytskyi.tasktracker.service;

import com.rsynytskyi.tasktracker.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);

    List<User> findAll();

    void addUser(User user);

    User updateUser(int id, User updatedUser);

    void deleteUser(String id);
}
