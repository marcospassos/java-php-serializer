package com.marcospassos.phpserializer.exclusion;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class NoExclusionStrategyTest
{
    private class A {
        public int field;
    }

    private class B {
        public int field;
    }

    @Test
    public void shouldSkipFieldAlwaysReturnsFalse() throws Exception
    {
        NoExclusionStrategy strategy = new NoExclusionStrategy();

        assertFalse(strategy.shouldSkipField(A.class.getField("field")));
        assertFalse(strategy.shouldSkipField(B.class.getField("field")));
    }
}