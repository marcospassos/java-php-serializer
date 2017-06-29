package com.marcospassos.phpserializer.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class BooleanAdapterTest
{
    @Test
    public void write() throws Exception
    {
        BooleanAdapter adapter = new BooleanAdapter();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write(true, writer, context);

        verify(writer, times(1)).writeBoolean(true);
    }
}