package com.marcospassos.phpserializer;

import com.marcospassos.phpserializer.adapter.IntegerAdapter;
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
}