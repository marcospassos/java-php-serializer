package com.phpcommon.phpserializer.adapter;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.TypeAdapter;
import com.phpcommon.phpserializer.Writer;

/**
 * Adapter for boolean values.
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
