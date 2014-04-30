package com.softserve.edu.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldForSearchContrroller<T> {

	Class<T> clazz;

	public FieldForSearchContrroller(Class<T> clazz) {
		this.clazz = clazz;
	}

	Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> findAnnot()  {

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(FieldForSearch.class)) {
				FieldForSearch searchField = fields[i]
						.getAnnotation(FieldForSearch.class);
				map.put(fields[i].getName(), searchField.nameForView());

			}
		}
		return map;
	}
}
