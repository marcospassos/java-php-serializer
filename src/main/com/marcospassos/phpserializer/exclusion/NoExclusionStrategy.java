package com.marcospassos.phpserializer.exclusion;

import com.marcospassos.phpserializer.FieldExclusionStrategy;
import java.lang.reflect.Field;

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
