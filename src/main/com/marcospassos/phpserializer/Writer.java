
package com.marcospassos.phpserializer;

import com.marcospassos.phpserializer.state.WritingValueState;

import java.lang.reflect.Modifier;

/**
 * Writes a values in PHP's serialization format.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class Writer
{
    /**
     * The buffer that stores the data.
     */
    private StringBuffer buffer;

    /**
     * The current state of the writer.
     */
    private WriterState state;

    /**
     * The reference counter.
     */
    private int pointer = 1;

    /**
     * Creates a new writer using an internal buffer.
     */
    public Writer()
    {
        this(new StringBuffer());
    }

    /**
     * Creates a writer specifying a buffer to write the data to.
     *
     * @param buffer The buffer to write the data to.
     */
    public Writer(StringBuffer buffer)
    {
        this.buffer = buffer;
        this.state = new WritingValueState();
    }

    /**
     * Returns the current result.
     *
     * @return The serialized data.
     */
    public String getResult()
    {
        return this.buffer.toString();
    }

    /**
     * Writes a custom serialized object to the buffer.
     *
     * @param className The fully-qualified name of the class.
     * @param data The serialized data.
     */
    public void writeObject(String className, String data)
    {
        setState(state.value());

        buffer.append("C:");
        buffer.append(className.length());
        buffer.append(":\"");
        buffer.append(className);
        buffer.append("\":");
        buffer.append(data.length());
        buffer.append(":{");
        buffer.append(data);
        buffer.append("}");
    }

    /**
     * Writes the start of an object to the buffer.
     *
     * @param className The fully-qualified name of the class.
     * @param fieldCount The number of fields.
     */
    public void writeObjectStart(String className, int fieldCount)
    {
        setState(state.objectBegin());

        buffer.append("O:");
        buffer.append(className.length());
        buffer.append(":\"");
        buffer.append(className);
        buffer.append("\":");
        buffer.append(fieldCount);
        buffer.append(":{");
    }

    /**
     * Writes an object property to the buffer.
     *
     * @param name The property name.
     * @param className The fully-qualified name of the class.
     * @param modifiers The property modifiers.
     */
    public void writeProperty(String name, String className, int modifiers)
    {
        if (Modifier.isPrivate(modifiers)) {
            writePrivateProperty(name, className);
        } else if (Modifier.isProtected(modifiers)) {
            writeProtectedProperty(name);
        } else {
            writeProperty(name);
        }
    }

    /**
     * Writes an object property to the buffer.
     *
     * @param name The property name.
     */
    private void writeProperty(String name)
    {
        setState(state.property());

        buffer.append("s:");
        buffer.append(name.length());
        buffer.append(":\"");
        buffer.append(name);
        buffer.append("\";");
    }

    /**
     * Writes a public object property to the buffer.
     *
     * @param name The property name.
     */
    public void writePublicProperty(String name)
    {
        writeProperty(name);
    }

    /**
     * Writes a protected object property to the buffer.
     *
     * @param name The property name.
     */
    public void writeProtectedProperty(String name)
    {
        writeProperty("\0*\0" + name);
    }

    /**
     * Writes a private object property to the buffer.
     *
     * @param name The property name.
     */
    public void writePrivateProperty(String name, String className)
    {
        writeProperty("\0" + className + "\0" + name);
    }

    /**
     * Writes the end of an object to the buffer.
     */
    public void writeObjectEnd()
    {
        setState(state.objectEnd());

        buffer.append("}");
    }

    /**
     * Writes a variable reference to the buffer.
     *
     * @param index The index of the value within the serialized data.
     */
    public void writeVariableReference(Integer index)
    {
        setState(state.value());

        buffer.append("R:");
        buffer.append(index);
        buffer.append(";");
    }

    /**
     * Writes an object reference to the buffer.
     *
     * @param index The index of the object within the serialized data.
     */
    public void writeObjectReference(Integer index)
    {
        setState(state.value());

        buffer.append("r:");
        buffer.append(index);
        buffer.append(";");
    }

    /**
     * Writes NULL to the buffer.
     */
    public void writeNull()
    {
        setState(state.value());

        buffer.append("N;");
    }

    /**
     * Writes a string value to the buffer.
     *
     * @param value The value.
     */
    public void writeString(String value)
    {
        setState(state.value());

        buffer.append("s:");
        buffer.append(value.length());
        buffer.append(":\"");
        buffer.append(value.replaceAll("\"", "\\\\\""));
        buffer.append("\";");
    }

    /**
     * Writes a boolean value to the buffer.
     *
     * @param value The value.
     */
    public void writeBoolean(Boolean value)
    {
        setState(state.value());

        buffer.append("b:");
        buffer.append(value ? 1 : 0);
        buffer.append(';');
    }

    /**
     * Writes an integer value to the buffer.
     *
     * @param value The value.
     */
    public void writeInteger(Integer value)
    {
        setState(state.value());

        buffer.append("i:");
        buffer.append(value);
        buffer.append(';');
    }

    /**
     * Writes a float value to the buffer.
     *
     * @param value The value.
     */
    public void writeFloat(Double value)
    {
        setState(state.value());

        buffer.append("d:");
        buffer.append(value);
        buffer.append(';');
    }

    /**
     * Writes the start of an array to the buffer.
     *
     * @param length The length of the array.
     */
    public void writeArrayStart(int length)
    {
        setState(state.arrayBegin());

        buffer.append("a:");
        buffer.append(length);
        buffer.append(":{");
    }

    /**
     * Writes the key of an array entry to the buffer.
     *
     * @param index The key of the array entry.
     */
    public void writeKey(int index)
    {
        setState(state.key());

        buffer.append("i:");
        buffer.append(index);
        buffer.append(';');
    }

    /**
     * Writes the key of an array entry to the buffer.
     *
     * @param key The key of the array entry.
     */
    public void writeKey(String key)
    {
        setState(state.key());

        buffer.append("s:");
        buffer.append(key.length());
        buffer.append(":\"");
        buffer.append(key.replaceAll("\"", "\\\\\""));
        buffer.append("\";");
    }

    /**
     * Writes the end of an array to the buffer.
     */
    public void writeArrayEnd()
    {
        setState(state.arrayEnd());

        buffer.append("}");
    }

    /**
     * Get the current reference count.
     *
     * @return The current reference count.
     */
    public int getPointer()
    {
        return this.pointer;
    }

    /**
     * Sets the state of the writer.
     *
     * @param state The new state.
     */
    private void setState(WriterState state)
    {
        if (state.isReferable()) {
            pointer++;
        }

        this.state = state;
    }
}
