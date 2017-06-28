package com.phpcommon.phpserializer.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple utility class for working with the reflection API and handling
 * reflection exceptions.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class ReflectionUtils
{
    /**
     * Get all declared fields on the specified class and all superclasses.
     *
     * The fields follow the order in which they are declared (direct fields
     * take precedence over inherited fields).
     *
     * The list does not include synthetic fields, such as {@code this$0},
     * {@code this$1}..
     *
     * @param type The class to collect the fields.
     *
     * @return The list of fields.
     */
    public static Field[] getFields(Class type)
    {
        List<Field> declaredFields = new ArrayList<>(
            Arrays.asList(type.getDeclaredFields())
        );

        Class<?> current = type;

        while (current.getSuperclass() != null) {
            current = current.getSuperclass();
            declaredFields.addAll(Arrays.asList(current.getDeclaredFields()));
        }

        List<Field> fields = new ArrayList<>();

        for (Field field : declaredFields) {
            // Excludes special properties from inner classes
            if (field.getName().indexOf("this$") != 0) {
                fields.add(field);
            }
        }

        return fields.toArray(new Field[0]);
    }

    /**
     * Gets the value of the specified field from the provided object.
     *
     * Java language access checking is suppressed to ensure that no exception
     * will be thrown for inaccessible fields.
     *
     * @param object Object from which the represented field's value is to be
     * extracted.
     * @param field The field to get the value from.
     *
     * @return The value of the represented field in object ; primitive values
     * are wrapped in an appropriate object before being returned.
     */
    public static Object getValue(Object object, Field field)
    {
        field.setAccessible(true);

        try {
            return field.get(object);
        } catch (IllegalAccessException exception) {
            throw new AssertionError(exception);
        }
    }

}
