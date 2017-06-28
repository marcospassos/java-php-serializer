package com.phpcommon.phpserializer;

import java.lang.reflect.Field;

/**
 * A strategy definition to decide whether or not a field should be excluded
 * from serialization result.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public interface FieldExclusionStrategy
{
    /**
     * Checks whether the specified field should be ignored.
     *
     * @param field The field that is under test.
     *
     * @return {@code true} if the field should be ignored, or {@code false}
     * otherwise.
     */
    boolean shouldSkipField(Field field);
}
