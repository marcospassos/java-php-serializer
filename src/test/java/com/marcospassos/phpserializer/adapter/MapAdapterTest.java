package com.marcospassos.phpserializer.adapter;

import java.util.HashMap;
import java.util.Map;
import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class MapAdapterTest
{
    @Test
    public void write() throws Exception
    {
        MapAdapter<Object, Object> adapter = new MapAdapter<>();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);
        Map<Object, Object> map = new HashMap<>();
        map.put(1, "a");
        map.put("2", "b");
        map.put(3, "c");

        // The verify check is split by entries once HashMap is an unordered map

        adapter.write(map, writer, context);

        InOrder order = inOrder(writer, context);

        order.verify(writer).writeArrayStart(3);

        InOrder first = inOrder(writer, context);

        first.verify(writer).writeKey(1);
        first.verify(context).write("a", writer);

        InOrder second = inOrder(writer, context);

        second.verify(writer).writeKey("2");
        second.verify(context).write("b", writer);

        InOrder third = inOrder(writer, context);

        third.verify(writer).writeKey(3);
        third.verify(context).write("c", writer);

        order.verify(writer).writeArrayEnd();

        order.verifyNoMoreInteractions();
    }
}