package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for {@code Integer} values.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class IntegerAdapter implements TypeAdapter<Integer>
{
    @Override
    public void write(Integer value, Writer writer, Context context)
    {
        writer.writeInteger(value);
    }
}
