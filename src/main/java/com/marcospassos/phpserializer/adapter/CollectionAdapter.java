package com.marcospassos.phpserializer.adapter;

import java.util.Collection;
import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for handling collections.
 *
 * @param <T> The type of values in the collection.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class CollectionAdapter<T> implements TypeAdapter<Collection<T>>
{
    @Override
    public void write(Collection<T> collection, Writer writer, Context context)
    {
        int size = collection.size();

        writer.writeArrayStart(size);

        int index = 0;
        for (Object value: collection) {
            writer.writeKey(index++);
            context.write(value, writer);
        }

        writer.writeArrayEnd();
    }
}
