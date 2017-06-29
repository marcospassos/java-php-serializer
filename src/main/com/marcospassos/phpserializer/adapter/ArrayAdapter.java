package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import com.marcospassos.phpserializer.TypeAdapter;

import java.lang.reflect.Array;

/**
 * Adapter for handling arrays.
 *
 * @param <T> The type of values in the list.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class ArrayAdapter<T> implements TypeAdapter<T>
{
    @Override
    public void write(T array, Writer writer, Context context)
    {
        Class type = array.getClass();

        if (!type.isArray()) {
            throw new IllegalArgumentException(
                "Expected array, but got " + type.getName());
        }

        int length = Array.getLength(array);

        writer.writeArrayStart(length);

        for (int index = 0; index < length; index++) {
            writer.writeKey(index);

            context.write(Array.get(array, index), writer);
        }

        writer.writeArrayEnd();
    }
}