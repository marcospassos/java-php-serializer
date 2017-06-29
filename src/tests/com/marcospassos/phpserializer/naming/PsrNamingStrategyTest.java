package com.marcospassos.phpserializer.naming;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class PsrNamingStrategyTest
{
    private class Foo {
        public int BaR;
    }

    @Test
    public void getClassNameConvertsNameInStudlyCaps() throws Exception
    {
        PsrNamingStrategy strategy = new PsrNamingStrategy();
        String className = strategy.getClassName(PsrNamingStrategyTest.class);

        assertEquals(
            "Com\\Marcospassos\\Phpserializer\\Naming\\PsrNamingStrategyTest",
            className
        );
    }

    @Test
    public void getFieldNameKeepsNameAsDeclared() throws Exception
    {
        Field field = Foo.class.getField("BaR");

        PsrNamingStrategy strategy = new PsrNamingStrategy();
        String fieldName = strategy.getFieldName(field);

        assertEquals("BaR", fieldName);
    }
}