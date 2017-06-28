package com.phpcommon.phpserializer.exclusion;

import static org.junit.Assert.*;

import com.phpcommon.phpserializer.FieldExclusionStrategy;
import java.lang.reflect.Field;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class DisjunctionExclusionStrategyTest
{
    class A {
        private int field;
    }

    @Test
    public void shouldSkipFieldPerformsShortCircuitOrOperation() throws Exception
    {
        FieldExclusionStrategy a = mock(FieldExclusionStrategy.class);
        FieldExclusionStrategy b = mock(FieldExclusionStrategy.class);

        Field field = A.class.getDeclaredField("field");

        when(a.shouldSkipField(field)).thenReturn(true);

        FieldExclusionStrategy strategy = new DisjunctionExclusionStrategy(a, b);

        assertTrue(strategy.shouldSkipField(field));

        verify(a, times(1)).shouldSkipField(field);
        verify(b, times(0)).shouldSkipField(field);
    }

    @Test
    public void shouldSkipFieldReturnsFalseIfNoStrategyIsProvided() throws Exception
    {
        FieldExclusionStrategy strategy = new DisjunctionExclusionStrategy();
        Field field = A.class.getDeclaredField("field");

        assertFalse(strategy.shouldSkipField(field));
    }
}