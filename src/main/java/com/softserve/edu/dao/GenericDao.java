package com.softserve.edu.dao;

import java.util.List;

public interface GenericDao<T> {

    /**
     * Save new value.
     *
     * @param item Item.
     */
    void save(T item);

    /**
     * Update existing item.
     *
     * @param item Item.
     * @return true if saved successfully, false otherwise.
     */
    T update(T item);

    /**
     * Get existing object by its id.
     *
     * @param objectClass Id of object.
     * @return Object is returned; null, if not found.
     */
    T findById(Class<T> objectClass, long id);

    /**
     * Get all objects from table.
     *
     * @return List of objects.
     */
    List<T> findAll(Class<T> objectClass);

    /**
     * Delete existing item.
     *
     * @param item Item.
     */
    void delete(T item);

    /**
     *
     */
    T findByUuid(Class<T> objectClass, String uuid);

    /**
     *
     */
    T findEntity(String singleQuery, Object... params);

    /**
     *
     */
    List<T> findEntityList(String singleQuery, Object... params);

    /**
     * Method for finding objects in the DB in some range.
     *
     * @param isFirstChar true - search from first letter
     * @param startPosition start position
     * @param maxResult max results
     * @param parameter some parameter to find, String
     * @param pattern pattern for searching, String
     * @return list of objects
     */
    List<T> dynamicSearch(int startPosition, int maxResult,
            String parameter, String pattern, boolean isFirstChar,
            Class<T> objectClass);
}
