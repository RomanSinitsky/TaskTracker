package com.rsynytskyi.tasktracker.dao;

import com.rsynytskyi.tasktracker.model.Task;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class TaskDaoImpl extends AbstractDao<Integer, Task> implements TaskDao {

    @Override
    public Task findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Task> findAll(String sorted) {
        enableBuilderCriteriaRoot();
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sorted)));
        List<Task> tasks = getSession().createQuery(criteriaQuery).list();
        if (sorted == "user") {
            Collections.sort(tasks, new UserComparator());
        }
        return tasks;
    }

    @Override
    public void save(Task task) {
        persist(task);
    }

    static class UserComparator implements Comparator<Task> {

        @Override
        public int compare(Task t1, Task t2) {
            return t1.getUser().getId() - t2.getUser().getId();
        }
    }
}
