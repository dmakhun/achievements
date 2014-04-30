package com.softserve.edu.dao;

import java.util.List;

public interface GenericDao<T> {

	/**
	 * Save new value.
	 * 
	 * @param item
	 *            Item.
	 * @return true if saved successfully, false otherwise.
	 */
	void save(T item);

	/**
	 * Update existing item.
	 * 
	 * @param item
	 *            Item.
	 * @return true if saved successfully, false otherwise.
	 */
	T update(T item);

	/**
	 * Get existing object by its id.
	 * 
	 * @param objectId
	 *            Id of object.
	 * @return Object is returned; null, if not found.
	 */
	T findById(Class<T> objectClass, Long id);

	/**
	 * Get all objects from table.
	 * 
	 * @return List of objects.
	 */
	List<T> findAll(Class<T> objectClass);

	/**
	 * Delete existing item.
	 * 
	 * @param item
	 *            Item.
	 * @return true if deleted; false otherwise.
	 */
	void delete(T item);

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	T findByUuid(Class<T> objectClass, String uuid);

	/**
	 * Method for finding certain objects in db in some range.
	 * 
	 * @param startPosition
	 *            start position
	 * @param maxResult
	 *            max results
	 * @param parameter1
	 *            some parameter to find, String
	 * @param resultString
	 *            pattern for searching, String
	 * @param findCriteria
	 *            true - search from first letter
	 * @param resultLong
	 *            pattern for searching, Long
	 * @param parameter2
	 *            some parameter to find, Long
	 * @return list of objects
	 */
	public List<T> dynamicSearchTwoCriterias(int startPosition, int maxResult,
			String parameter1, String resultString, boolean findCriteria,
			Long resultLong, String parameter2, Class<T> entity);

	/**
	 * 
	 * @param singleQuery
	 * @param params
	 * @return
	 */
	T findEntity(String singleQuery, Object... params);

	/**
	 * 
	 * @param singleQuery
	 * @param params
	 * @return
	 */
	List<T> findEntityList(String singleQuery, Object... params);
}
