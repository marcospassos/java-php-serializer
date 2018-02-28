package com.marcospassos.phpserializer.adapter;

import java.nio.charset.Charset;
import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class StringAdapterTest
{
    @Test
    public void writeUsesUtf8ByDefault() throws Exception
    {
        Charset charset = Charset.forName("UTF-8");

        StringAdapter adapter = new StringAdapter();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write("foo", writer, context);

        verify(writer).writeString("foo", charset);

        verifyNoMoreInteractions(writer);
    }

    @Test
    public void writeCustomCharset() throws Exception
    {
        Charset charset = Charset.forName("ISO-8859-1");
        StringAdapter adapter = new StringAdapter(charset);
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write("foo", writer, context);

        verify(writer).writeString("foo", charset);

        verifyNoMoreInteractions(writer);
    }
}