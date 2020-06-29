package com.rsynytskyi.tasktracker.dao;

import com.rsynytskyi.tasktracker.model.Task;

import java.util.List;

public interface TaskDao {

    Task findById(int id);

    List<Task> findAll(String sorted);

    void save(Task user);

    void delete(Task user);

    List<String> getColumns();
}
