package com.rsynytskyi.tasktracker.controller;

import com.rsynytskyi.tasktracker.model.Task;
import com.rsynytskyi.tasktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/addTask")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @PostMapping("/task/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(Integer.parseInt(id), task);
        return updatedTask;
    }

    @GetMapping("/task/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTask(@PathVariable String id) {
        return taskService.findById(Integer.parseInt(id));
    }

    @GetMapping("/allTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks(@RequestParam(required = false, defaultValue = "ID") String sort) {
        List<Task> tasks = taskService.findAll(sort);
        return tasks;
    }

    @GetMapping("/task/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }
}
