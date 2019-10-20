package com.example.jdbctemplate.utils;

/**
 * DataConvertUtils
 *
 * @author star
 */
public final class DataConvertUtils {

    public static String getString(Object object) {
        return String.valueOf(object);
    }

    public static Long getLong(Object object) {
        if (object == null) {
            return null;
        }
        return Long.valueOf(getString(object));
    }

}
