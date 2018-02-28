package com.marcospassos.phpserializer;

import java.util.HashMap;
import java.util.Map;
import com.marcospassos.phpserializer.adapter.IntegerAdapter;
import com.marcospassos.phpserializer.adapter.MapAdapter;
import com.marcospassos.phpserializer.adapter.StringAdapter;
import com.marcospassos.phpserializer.exclusion.NoExclusionStrategy;
import com.marcospassos.phpserializer.naming.PsrNamingStrategy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class SerializerTest
{
    @Test
    public void serialize() throws Exception
    {
        AdapterRegistry registry = new AdapterRegistry();
        registry.registerAdapter(Integer.class, new IntegerAdapter());

        Serializer serializer = new Serializer(
            new PsrNamingStrategy(),
            new NoExclusionStrategy(),
            registry
        );

        assertEquals("i:1;", serializer.serialize(1));
    }

    @Test
    public void serializeGenericAdapter() throws Exception
    {
        AdapterRegistry registry = new AdapterRegistry();
        registry.registerAdapter(Map.class, new MapAdapter());
        registry.registerAdapter(String.class, new StringAdapter());
        registry.registerAdapter(Integer.class, new IntegerAdapter());

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "foo");
        map.put(2, "bar");

        Serializer serializer = new Serializer(
            new PsrNamingStrategy(),
            new NoExclusionStrategy(),
            registry
        );

        String result = serializer.serialize(map);

        assertEquals("a:2:{i:1;s:3:\"foo\";i:2;s:3:\"bar\";}", result);
    }
}