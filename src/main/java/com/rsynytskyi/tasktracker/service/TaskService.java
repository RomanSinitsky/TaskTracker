package com.rsynytskyi.tasktracker.service;

import com.rsynytskyi.tasktracker.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    Task findById(int id);

    List<Task> findAll(String sorted);

    Task updateTask(int id, Task updatedTask);

    void deleteTask(String id);
}
