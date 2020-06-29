package com.rsynytskyi.tasktracker.dao;

import com.rsynytskyi.tasktracker.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Override
    public User findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<User> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        return (List<User>)criteria.list();
    }

    @Override
    public void save(User user) {
        persist(user);
    }
}