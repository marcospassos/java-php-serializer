package com.phpcommon.phpserializer.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.Writer;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class IntegerAdapterTest
{
    @Test
    public void write() throws Exception
    {
        IntegerAdapter adapter = new IntegerAdapter();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write(10, writer, context);

        verify(writer, times(1)).writeInteger(10);
    }
}