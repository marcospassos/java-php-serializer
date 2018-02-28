package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for {@code Boolean} type.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class BooleanAdapter implements TypeAdapter<Boolean>
{
    @Override
    public void write(Boolean value, Writer writer, Context context)
    {
        writer.writeBoolean(value);
    }
}
