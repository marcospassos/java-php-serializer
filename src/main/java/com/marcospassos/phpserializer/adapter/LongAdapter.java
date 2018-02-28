package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for {@code Long} values.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class LongAdapter implements TypeAdapter<Long>
{
    @Override
    public void write(Long value, Writer writer, Context context)
    {
        writer.writeInteger(value);
    }
}
