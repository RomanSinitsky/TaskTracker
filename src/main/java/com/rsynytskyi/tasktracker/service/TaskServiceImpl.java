package com.rsynytskyi.tasktracker.service;

import com.rsynytskyi.tasktracker.dao.TaskDao;
import com.rsynytskyi.tasktracker.dao.UserDao;
import com.rsynytskyi.tasktracker.model.Task;
import com.rsynytskyi.tasktracker.model.TaskStatus;
import com.rsynytskyi.tasktracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;


    @Override
    public void addTask(Task task) {
        task.setUser(getUserFromAuthentification());
        task.setCreated(new Date());
        taskDao.save(task);
    }

    @Override
    public Task findById(int id) {
        return taskDao.findById(id);
    }

    @Override
    public List<Task> findAll(String sorted) {
        List<String> columns = taskDao.getColumns();
        if (!columns.contains(sorted)) {
            String reduce = columns.stream().reduce("", (acc, appender) -> {
                if (!"".equals(acc)) {
                    return (acc + ", " + appender);
                } else {
                    return appender;
                }
            });
            Task task = new Task();
            task.setError("parameter should be in " + reduce);
            return List.of(task);
        }
        return taskDao.findAll(sorted);
    }

    @Override
    public Task updateTask(int id, Task updatedTask) {
        Task forUpdateTask = taskDao.findById(id);
        forUpdateTask.setTitle(updatedTask.getTitle());
        forUpdateTask.setDescription(updatedTask.getDescription());

        String status = updatedTask.getStatus();
        switch (status) {
            case "V":
                forUpdateTask.setStatus(TaskStatus.VIEW.getState());
                break;
            case "P":
                forUpdateTask.setStatus(TaskStatus.IN_PROGRESS.getState());
                break;
            case "D":
                forUpdateTask.setStatus(TaskStatus.DONE.getState());
                break;
            default:
                forUpdateTask.setError("Status must be in ('V' - view, 'P' - in progress, 'D' - done");
        }

        Integer newUserId = updatedTask.getUser().getId();
        User newUser = userDao.findById(newUserId);

        forUpdateTask.setUser(newUser);
        return forUpdateTask;
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskDao.findById(Integer.parseInt(id));
        taskDao.delete(task);
    }

    private User getUserFromAuthentification() {
        String userName = null;
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details instanceof UserDetails) {
            userName = ((UserDetails) details).getUsername();
        } else {
            userName = details.toString();
        }
        User user = userDao.findById(Integer.parseInt(userName));
        return user;
    }

}
