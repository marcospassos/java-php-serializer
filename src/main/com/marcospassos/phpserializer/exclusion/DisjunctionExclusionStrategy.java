package com.marcospassos.phpserializer.exclusion;

import com.marcospassos.phpserializer.FieldExclusionStrategy;

import java.lang.reflect.Field;

/**
 * Aggregates multiple exclusion strategies to perform a short-circuit OR
 * operation.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class DisjunctionExclusionStrategy implements FieldExclusionStrategy
{
    /**
     * The underlying strategies.
     */
    private FieldExclusionStrategy[] strategies;

    /**
     * Creates a disjunction from the specified strategies.
     */
    public DisjunctionExclusionStrategy(FieldExclusionStrategy... strategies)
    {
        this.strategies = strategies;
    }

    @Override
    public boolean shouldSkipField(Field field)
    {
        for (FieldExclusionStrategy strategy : strategies) {
            if (strategy.shouldSkipField(field)) {
                return true;
            }
        }

        return false;
    }
}
