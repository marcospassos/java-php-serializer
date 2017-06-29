package com.marcospassos.phpserializer.adapter;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

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
