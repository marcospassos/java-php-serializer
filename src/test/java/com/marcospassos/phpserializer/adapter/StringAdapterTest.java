package com.marcospassos.phpserializer.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class StringAdapterTest
{
    @Test
    public void write() throws Exception
    {
        StringAdapter adapter = new StringAdapter();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write("foo", writer, context);

        verify(writer).writeString("foo");
    }
}