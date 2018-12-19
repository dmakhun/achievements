package com.softserve.edu.dao.impl;

import static com.softserve.edu.util.Constants.ROLE_MANAGER;

import com.softserve.edu.dao.AccessRoleRepository;
import com.softserve.edu.dao.GenericDao;
import com.softserve.edu.manager.RoleManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDaoImpl<T> implements GenericDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private AccessRoleRepository accessRoleRepository;

    GenericDaoImpl() {
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        T mergedEntity = entityManager.merge(entity);
        entityManager.remove(mergedEntity);

    }

    @Override
    public T findById(Class<T> objectClass, long id) {
        return entityManager.find(objectClass, id);
    }

    @Override
    public T findEntity(String singleQuery, Object... params) {
        try {
            Query query = entityManager.createNamedQuery(singleQuery);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.warn("Cannot find entity", ex);
            return null;
        }
    }

    @Override
    public List<T> findEntityList(String singleQuery, Object... params) {
        Query query = entityManager.createNamedQuery(singleQuery);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return query.getResultList();
    }

    @Override
    public List<T> findAll(Class<T> objectClass) {
        return entityManager.createQuery("from " + objectClass.getName())
                .getResultList();
    }

    @Override
    public List<T> dynamicSearch(int startPosition, int maxResult,
            String parameter, String pattern, boolean isFirstChar,
            Class<T> objectClass) {
        String placeholder = isFirstChar ? "" : "%";

        List<T> resultList = (List<T>) entityManager
                .createQuery(
                        "from " + objectClass.getName()
                                + " where " + parameter + " like :pattern and accessrole="
                                + accessRoleRepository.findByName(ROLE_MANAGER).getId())
                .setParameter("pattern", placeholder + pattern + "%")
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();
        return resultList;
    }
}