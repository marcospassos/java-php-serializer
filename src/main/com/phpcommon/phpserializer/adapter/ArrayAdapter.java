package com.phpcommon.phpserializer.adapter;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.Writer;
import com.phpcommon.phpserializer.TypeAdapter;

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