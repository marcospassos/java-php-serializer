package com.phpcommon.phpserializer;

import static org.junit.Assert.*;

import com.phpcommon.phpserializer.adapter.IntegerAdapter;
import com.phpcommon.phpserializer.exclusion.NoExclusionStrategy;
import com.phpcommon.phpserializer.naming.PsrNamingStrategy;
import org.junit.Test;

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