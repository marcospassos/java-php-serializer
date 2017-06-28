package com.phpcommon.phpserializer.adapter;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.TypeAdapter;
import com.phpcommon.phpserializer.Writer;

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
