package com.phpcommon.phpserializer.adapter;

import java.util.List;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.Writer;
import com.phpcommon.phpserializer.TypeAdapter;

/**
 * Adapter for handling lists.
 *
 * @param <T> The type of values in the list.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class ListAdapter<T> implements TypeAdapter<List<T>>
{
    @Override
    public void write(List<T> list, Writer writer, Context context)
    {
        int size = list.size();

        writer.writeArrayStart(size);

        for (int index = 0; index < size; index++) {
            writer.writeKey(index);

            context.write(list.get(index), writer);
        }

        writer.writeArrayEnd();
    }
}
