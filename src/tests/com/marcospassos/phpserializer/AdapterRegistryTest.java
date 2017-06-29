package com.marcospassos.phpserializer;

import org.junit.Test;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class AdapterRegistryTest
{
    @Test
    public void getAdapterReturnsMostSpecializedAdapter() throws Exception
    {
        TypeAdapter numberAdapter = mock(TypeAdapter.class);
        TypeAdapter integerAdapter = mock(TypeAdapter.class);
        TypeAdapter doubleAdapter = mock(TypeAdapter.class);

        Map<Class, TypeAdapter> adapters = new LinkedHashMap<>();
        adapters.put(Double.class, doubleAdapter);
        adapters.put(Number.class, numberAdapter);
        adapters.put(Integer.class, integerAdapter);

        AdapterRegistry registry = new AdapterRegistry(adapters);

        assertSame(doubleAdapter, registry.getAdapter(Double.class));
        assertSame(integerAdapter, registry.getAdapter(Integer.class));
        assertSame(numberAdapter, registry.getAdapter(Float.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAdapterFailsIfNoAdapterIsFound() throws Exception
    {
        AdapterRegistry registry = new AdapterRegistry();
        registry.getAdapter(Integer.class);
    }

    @Test
    public void getAdaptersReturnsMapOfAdapters() throws Exception
    {
        TypeAdapter adapterInteger = mock(TypeAdapter.class);
        TypeAdapter adapterFloat = mock(TypeAdapter.class);

        AdapterRegistry registry = new AdapterRegistry();
        registry.registerAdapter(Integer.class, adapterInteger);
        registry.registerAdapter(Float.class, adapterFloat);

        Map<Class, TypeAdapter> adapters = registry.getAdapters();

        assertEquals(2, adapters.size());
        assertTrue(adapters.containsKey(Integer.class));
        assertTrue(adapters.containsValue(adapterInteger));
        assertTrue(adapters.containsKey(Float.class));
        assertTrue(adapters.containsValue(adapterFloat));
    }
}