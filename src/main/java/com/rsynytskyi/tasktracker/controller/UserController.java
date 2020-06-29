package com.rsynytskyi.tasktracker.controller;


import com.rsynytskyi.tasktracker.model.User;
import com.rsynytskyi.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping(path = "/user/{id}")
    public User getUser(@PathVariable String id) {
        User user = userService.findById(Integer.parseInt(id));
        return user;
    }

    @PostMapping(path = "/user/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(Integer.parseInt(id), user);
        updatedUser.setPassword("undisplayable");
        return updatedUser;
    }

    @GetMapping(path = "/user/{id}/delete")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping(path = "/allUsers")
    List<User> getAllUsers() {
        List<User> all = userService.findAll().stream().peek(u -> u.setPassword("undisplayable")).collect(Collectors.toList());
        return all;
    }
}
