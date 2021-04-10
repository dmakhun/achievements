package com.edu.academy.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldForSearchController<T> {

    private final Class<T> clazz;
    private final Map<String, String> map = new HashMap<>();

    public FieldForSearchController(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Map<String, String> findAnnotation() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldForSearch.class)) {
                FieldForSearch searchField = field
                        .getAnnotation(FieldForSearch.class);
                map.put(field.getName(), searchField.nameForView());

            }
        }
        return map;
    }
}
