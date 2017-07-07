package com.marcospassos.phpserializer.exclusion;

import java.lang.reflect.Field;
import com.marcospassos.phpserializer.FieldExclusionStrategy;

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
     *
     * @param strategies The list of strategies.
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
