package com.phpcommon.phpserializer;

import java.lang.reflect.Field;

/**
 * Translates the name of classes and properties according to the target domain.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public interface NamingStrategy
{
    /**
     * Returns the fully-qualified name for the specified class.
     *
     * @param type The class to determine the serialized name.
     *
     * @return The fully-qualified name for the specified class.
     */
    String getClassName(Class type);

    /**
     * Returns the name for the specified field.
     *
     * @param field The field to determine the serialized name.
     *
     * @return The serialized name for the specified field.
     */
    String getFieldName(Field field);
}
