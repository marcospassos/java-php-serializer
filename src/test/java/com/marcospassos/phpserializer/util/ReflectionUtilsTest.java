package com.marcospassos.phpserializer.util;

import java.lang.reflect.Field;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class ReflectionUtilsTest
{
    private class A
    {
        private int a;
    }

    private class B extends A
    {
        private int b;
    }

    private class C extends B
    {
        private int c;

        public C(int c)
        {
            this.c = c;
        }
    }

    @Test
    public void getFieldsReturnsAllFieldsFromClassAndSuperclasses()
        throws Exception
    {
        assertArrayEquals(
            new Field[]{A.class.getDeclaredField("a")},
            ReflectionUtils.getFields(A.class)
        );

        assertArrayEquals(
            new Field[]{
                B.class.getDeclaredField("b"),
                A.class.getDeclaredField("a"),
            },
            ReflectionUtils.getFields(B.class)
        );

        assertArrayEquals(
            new Field[]{
                C.class.getDeclaredField("c"),
                B.class.getDeclaredField("b"),
                A.class.getDeclaredField("a")
            },
            ReflectionUtils.getFields(C.class)
        );
    }

    @Test
    public void getValue() throws Exception
    {
        C object = new C(1);
        Field field = C.class.getDeclaredField("c");

        assertEquals(1, ReflectionUtils.getValue(object, field));
    }
}