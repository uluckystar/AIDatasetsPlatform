package com.localaihub.platform.module.system.base.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/15 16:44
 */
public class RouterUtils {

    public static void filterEmptyFields(Object obj) {
        if (obj == null) {
            return;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value == null || value.equals(false) || value.equals(0)) {
                    field.set(obj, null);
                } else if (value instanceof List) {
                    List<?> list = (List<?>) value;
                    if (list.isEmpty()) {
                        field.set(obj, null);
                    } else {
                        for (Object item : list) {
                            filterEmptyFields(item);
                        }
                    }
                } else {
                    filterEmptyFields(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}