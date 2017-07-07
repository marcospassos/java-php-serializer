package com.marcospassos.phpserializer;

import java.lang.reflect.Modifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class WriterTest
{
    @Test
    public void writeObject() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObjectStart("Foo", 3);

        writer.writeProperty("a", "Foo", Modifier.PRIVATE);
        writer.writeInteger(1);

        writer.writeProperty("b", "Foo", Modifier.PROTECTED);
        writer.writeInteger(2);

        writer.writeProperty("c", "Foo", Modifier.PUBLIC);
        writer.writeInteger(3);

        writer.writeObjectEnd();

        assertEquals(
            "O:3:\"Foo\":3:{s:6:\"\u0000Foo\u0000a\";i:1;" +
                "s:4:\"\u0000*\u0000b\";i:2;s:1:\"c\";i:3;}",
            writer.getResult()
        );
    }

    @Test
    public void writeSerializableObject() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObject("Foo", "i:1");

        assertEquals("C:3:\"Foo\":3:{i:1}", writer.getResult());
    }

    @Test
    public void writePublicProperty() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObjectStart("Foo", 3);

        writer.writePublicProperty("a");
        writer.writeInteger(1);

        writer.writeObjectEnd();

        assertEquals("O:3:\"Foo\":3:{s:1:\"a\";i:1;}", writer.getResult());
    }

    @Test
    public void writeProtectedProperty() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObjectStart("Foo", 3);

        writer.writeProtectedProperty("a");
        writer.writeInteger(1);

        writer.writeObjectEnd();

        assertEquals(
            "O:3:\"Foo\":3:{s:4:\"\u0000*\u0000a\";i:1;}",
            writer.getResult()
        );
    }

    @Test
    public void writePrivateProperty() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObjectStart("Foo", 3);

        writer.writePrivateProperty("a", "Foo");
        writer.writeInteger(1);

        writer.writeObjectEnd();

        assertEquals(
            "O:3:\"Foo\":3:{s:6:\"\u0000Foo\u0000a\";i:1;}",
            writer.getResult()
        );
    }

    @Test
    public void writeVariableReference() throws Exception
    {
        Writer writer = new Writer();
        writer.writeVariableReference(1);

        assertEquals("R:1;", writer.getResult());
    }

    @Test
    public void writeObjectReference() throws Exception
    {
        Writer writer = new Writer();
        writer.writeObjectReference(1);

        assertEquals("r:1;", writer.getResult());
    }

    @Test
    public void writeNull() throws Exception
    {
        Writer writer = new Writer();
        writer.writeNull();

        assertEquals("N;", writer.getResult());
    }

    @Test
    public void writeString() throws Exception
    {
        Writer writer = new Writer();
        writer.writeString("foo");

        assertEquals("s:3:\"foo\";", writer.getResult());
    }

    @Test
    public void writeEmptyString() throws Exception
    {
        Writer writer = new Writer();
        writer.writeString("");

        assertEquals("s:0:\"\";", writer.getResult());
    }

    @Test
    public void writeBooleanTrue() throws Exception
    {
        Writer writer = new Writer();
        writer.writeBoolean(true);

        assertEquals("b:1;", writer.getResult());
    }

    @Test
    public void writeBooleanFalse() throws Exception
    {
        Writer writer = new Writer();
        writer.writeBoolean(false);

        assertEquals("b:0;", writer.getResult());
    }

    @Test
    public void writeInteger() throws Exception
    {
        Writer writer = new Writer();
        writer.writeInteger(1);

        assertEquals("i:1;", writer.getResult());
    }

    @Test
    public void writeFloat() throws Exception
    {
        Writer writer = new Writer();
        writer.writeFloat(1.0);

        assertEquals("d:1.0;", writer.getResult());
    }

    @Test
    public void writeArray() throws Exception
    {
        Writer writer = new Writer();
        writer.writeArrayStart(3);

        writer.writeKey("a");
        writer.writeInteger(1);

        writer.writeKey("b");
        writer.writeInteger(2);

        writer.writeKey(3);
        writer.writeInteger(3);

        writer.writeArrayEnd();

        assertEquals(
            "a:3:{s:1:\"a\";i:1;s:1:\"b\";i:2;i:3;i:3;}",
            writer.getResult()
        );
    }

    @Test
    public void pointerTracksReferableValues() throws Exception
    {
        Writer writer = new Writer();

        assertEquals(1, writer.getPointer());

        writer.writeArrayStart(2);

        assertEquals(1, writer.getPointer());

        writer.writeKey("a");

        assertEquals(2, writer.getPointer());

        writer.writeInteger(1);

        assertEquals(2, writer.getPointer());

        writer.writeKey("b");

        assertEquals(3, writer.getPointer());

        writer.writeInteger(2);

        assertEquals(3, writer.getPointer());

        writer.writeArrayEnd();

        assertEquals(3, writer.getPointer());
    }
}