package com.marcospassos.phpserializer.adapter;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;
import org.mockito.InOrder;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class ArrayAdapterTest
{
    @Test
    public void write() throws Exception
    {
        ArrayAdapter<Object> adapter = new ArrayAdapter<>();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);
        String[] array = new String[] {"a", "b", "c"};

        adapter.write(array, writer, context);

        InOrder order = inOrder(writer, context);

        order.verify(writer, times(1)).writeArrayStart(3);
        order.verify(writer, times(1)).writeKey(0);
        order.verify(context, times(1)).write("a", writer);
        order.verify(writer, times(1)).writeKey(1);
        order.verify(context, times(1)).write("b", writer);
        order.verify(writer, times(1)).writeKey(2);
        order.verify(context, times(1)).write("c", writer);
        order.verify(writer, times(1)).writeArrayEnd();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeFailsIfNotArray() throws Exception
    {
        ArrayAdapter<Object> adapter = new ArrayAdapter<>();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        adapter.write(new Object(), writer, context);}
}