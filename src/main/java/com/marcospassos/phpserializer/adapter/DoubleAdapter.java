package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for double values.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class DoubleAdapter implements TypeAdapter<Double>
{
    @Override
    public void write(Double value, Writer writer, Context context)
    {
        writer.writeFloat(value);
    }
}
