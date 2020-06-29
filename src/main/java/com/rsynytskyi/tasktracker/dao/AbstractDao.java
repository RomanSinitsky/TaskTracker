package com.rsynytskyi.tasktracker.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;
    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery<T> criteriaQuery;
    protected Root<T> root;
    private List<String> columns;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>)((ParameterizedType)this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];

        List<Field> fields = Arrays.asList(this.persistentClass.getDeclaredFields());
        this.columns = fields.stream().map(f -> f.getName()).collect(Collectors.toList());
    }

    @Autowired
    SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    public void persist(T entity) {
        getSession().persist(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }

    protected void enableBuilderCriteriaRoot() {
        this.criteriaBuilder = getSession().getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery(persistentClass);
        this.root = this.criteriaQuery.from(persistentClass);
    }

}
