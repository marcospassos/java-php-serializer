package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Decorates adapters for handling object references.
 *
 * This adapter checks whether an object has already been serialized. If it is
 * the case, it serializes the reference instead of the object itself.
 * Otherwise, it sets the reference to the object in the context for later use
 * before calling the decorated adapter.
 *
 * @param <T> The type of object.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class ReferableObjectAdapter<T> implements TypeAdapter<T>
{
    /**
     * The decorated adapter.
     */
    private TypeAdapter<T> adapter;

    /**
     * Decorates the specified adapter.
     *
     * @param adapter The adapter to decorate.
     */
    public ReferableObjectAdapter(TypeAdapter<T> adapter)
    {
        this.adapter = adapter;
    }

    @Override
    public void write(T object, Writer writer, Context context)
    {
        int pointer = context.getReference(object);

        if (pointer > -1) {
            writer.writeObjectReference(pointer);

            return;
        }

        context.setReference(writer.getPointer(), object);

        adapter.write(object, writer, context);
    }
}
