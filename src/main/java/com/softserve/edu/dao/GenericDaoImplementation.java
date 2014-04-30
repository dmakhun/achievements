package com.softserve.edu.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDaoImplementation<T> implements GenericDao<T> {

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger LOGGER = Logger
			.getLogger(GenericDaoImplementation.class);

	public GenericDaoImplementation() {
		super();
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
	public T findById(Class<T> objectClass, Long id) {
		return entityManager.find(objectClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findByUuid(Class<T> objectClass, String uuid) {
		try {
			T foundEntity = (T) entityManager
					.createQuery(
							"from " + objectClass.getName()
									+ " where uuid like :uuid")
					.setParameter("uuid", uuid).getSingleResult();
			return foundEntity;
		} catch (NoResultException exc) {
			LOGGER.warn("cannot find by uuid", exc);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findEntity(String singleQuery, Object... params) {
		try {
			Query query = entityManager.createNamedQuery(singleQuery);

			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}

			return (T) query.getSingleResult();
		} catch (NoResultException exc) {
			LOGGER.warn("cannot find", exc);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findEntityList(String singleQuery, Object... params) {
		Query query = entityManager.createNamedQuery(singleQuery);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> objectClass) {
		return entityManager.createQuery("from " + objectClass.getName())
				.getResultList();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> dynamicSearchTwoCriterias(int startPosition, int maxResult,
			String parameter1, String resultString, boolean findCriteria,
			Long resultLong, String parameter2, Class<T> objectClass) {
		String additionFind = "";
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(objectClass);
		Root<T> root = criteriaQuery.from(objectClass);

		criteriaQuery.select(root);

		if (findCriteria == false) {
			additionFind = "%";
		}

		criteriaQuery.where(criteriaBuilder.like(root.<String> get(parameter1),
				additionFind + resultString + "%"));
		if (resultLong != null) {
			criteriaQuery.where(criteriaBuilder.like(
					root.<String> get(parameter1), additionFind + resultString
							+ "%"), criteriaBuilder.equal(root.get(parameter2),
					resultLong));
		}
		TypedQuery typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(startPosition);
		typedQuery.setMaxResults(maxResult);
		List<T> resultList = typedQuery.getResultList();

		return resultList;
	}

}
