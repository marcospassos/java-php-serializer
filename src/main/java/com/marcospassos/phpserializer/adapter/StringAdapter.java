package com.marcospassos.phpserializer.adapter;

import java.nio.charset.Charset;
import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;

/**
 * Adapter for string type.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class StringAdapter implements TypeAdapter<String>
{
    /**
     * The charset.
     */
    private Charset charset;

    /**
     * Creates a adapter for strings encoded in UTF-8.
     */
    public StringAdapter() {
        this(Charset.forName("UTF-8"));
    }

    /**
     * Creates a adapter for strings encoded with the specified charset.
     *
     * @param charset
     */
    public StringAdapter(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void write(String value, Writer writer, Context context)
    {
        writer.writeString(value, this.charset);
    }
}
