package com.phpcommon.phpserializer.adapter;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.TypeAdapter;
import com.phpcommon.phpserializer.Writer;

/**
 * Adapter for handling strings.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class StringAdapter implements TypeAdapter<String>
{
    @Override
    public void write(String value, Writer writer, Context context)
    {
        writer.writeString(value);
    }
}
