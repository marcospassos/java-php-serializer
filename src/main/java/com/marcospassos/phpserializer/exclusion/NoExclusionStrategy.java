package com.marcospassos.phpserializer.exclusion;

import java.lang.reflect.Field;
import com.marcospassos.phpserializer.FieldExclusionStrategy;

/**
 * A strategy to exclude nothing.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class NoExclusionStrategy implements FieldExclusionStrategy
{
    @Override
    public boolean shouldSkipField(Field field)
    {
        return false;
    }
}
