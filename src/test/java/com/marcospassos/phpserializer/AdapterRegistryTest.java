package com.marcospassos.phpserializer;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

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
        TypeAdapter byteAdapter = mock(TypeAdapter.class);
        TypeAdapter longAdapter = mock(TypeAdapter.class);
        TypeAdapter booleanAdapter = mock(TypeAdapter.class);
        TypeAdapter charAdapter = mock(TypeAdapter.class);
        TypeAdapter shortAdapter = mock(TypeAdapter.class);
        TypeAdapter integerAdapter = mock(TypeAdapter.class);
        TypeAdapter doubleAdapter = mock(TypeAdapter.class);
        TypeAdapter collectionAdapter = mock(TypeAdapter.class);
        TypeAdapter setAdapter = mock(TypeAdapter.class);
        TypeAdapter hashSetAdapter = mock(TypeAdapter.class);
        TypeAdapter arrayOfIntegerAdapter = mock(TypeAdapter.class);
        TypeAdapter arrayOfObjectsAdapter = mock(TypeAdapter.class);

        Map<Class, TypeAdapter> adapters = new LinkedHashMap<>();
        adapters.put(Double.class, doubleAdapter);
        adapters.put(Byte.class, byteAdapter);
        adapters.put(Long.class, longAdapter);
        adapters.put(Boolean.class, booleanAdapter);
        adapters.put(Character.class, charAdapter);
        adapters.put(Short.class, shortAdapter);
        adapters.put(Set.class, setAdapter);
        adapters.put(Collection.class, collectionAdapter);
        adapters.put(Number.class, numberAdapter);
        adapters.put(HashSet.class, hashSetAdapter);
        adapters.put(Integer.class, integerAdapter);
        adapters.put(int[].class, arrayOfIntegerAdapter);
        adapters.put(Object[].class, arrayOfObjectsAdapter);

        AdapterRegistry registry = new AdapterRegistry(adapters);

        assertSame(doubleAdapter, registry.getAdapter(Double.class));
        assertSame(doubleAdapter, registry.getAdapter(double.class));
        assertSame(integerAdapter, registry.getAdapter(Integer.class));
        assertSame(integerAdapter, registry.getAdapter(int.class));
        assertSame(charAdapter, registry.getAdapter(Character.class));
        assertSame(charAdapter, registry.getAdapter(char.class));
        assertSame(longAdapter, registry.getAdapter(Long.class));
        assertSame(longAdapter, registry.getAdapter(long.class));
        assertSame(byteAdapter, registry.getAdapter(Byte.class));
        assertSame(byteAdapter, registry.getAdapter(byte.class));
        assertSame(shortAdapter, registry.getAdapter(Short.class));
        assertSame(shortAdapter, registry.getAdapter(short.class));
        assertSame(booleanAdapter, registry.getAdapter(Boolean.class));
        assertSame(booleanAdapter, registry.getAdapter(boolean.class));
        assertSame(numberAdapter, registry.getAdapter(Float.class));
        assertSame(numberAdapter, registry.getAdapter(float.class));
        assertSame(hashSetAdapter, registry.getAdapter(HashSet.class));
        assertSame(setAdapter, registry.getAdapter(TreeSet.class));
        assertSame(collectionAdapter, registry.getAdapter(List.class));
        assertSame(arrayOfIntegerAdapter, registry.getAdapter(int[].class));
        assertSame(arrayOfObjectsAdapter, registry.getAdapter(double[].class));
        assertSame(arrayOfObjectsAdapter, registry.getAdapter(Object[].class));
        assertSame(arrayOfObjectsAdapter, registry.getAdapter(Double[].class));
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