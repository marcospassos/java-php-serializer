package com.phpcommon.phpserializer.adapter;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.Writer;
import com.phpcommon.phpserializer.TypeAdapter;

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
