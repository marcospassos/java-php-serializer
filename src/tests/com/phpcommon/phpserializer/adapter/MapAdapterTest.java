package com.phpcommon.phpserializer.adapter;


import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.Writer;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mockito.InOrder;

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

        adapter.write(map, writer, context);

        InOrder order = inOrder(writer, context);

        order.verify(writer, times(1)).writeArrayStart(3);
        order.verify(writer, times(1)).writeKey(1);
        order.verify(context, times(1)).write("a", writer);
        order.verify(writer, times(1)).writeKey("2");
        order.verify(context, times(1)).write("b", writer);
        order.verify(writer, times(1)).writeKey(3);
        order.verify(context, times(1)).write("c", writer);
        order.verify(writer, times(1)).writeArrayEnd();
    }
}