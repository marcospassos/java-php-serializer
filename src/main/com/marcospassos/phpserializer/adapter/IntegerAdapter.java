package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import com.marcospassos.phpserializer.TypeAdapter;

/**
 * Adapter for boolean values.
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
