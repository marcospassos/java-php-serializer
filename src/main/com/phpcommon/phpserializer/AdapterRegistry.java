package com.phpcommon.phpserializer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Stores and resolves adapters for types.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class AdapterRegistry
{
    /**
     * The list of adapters.
     */
    private List<TypeAdapter> adapters;

    /**
     * The list of classes.
     */
    private List<Class> classes;

    /**
     * Creates a registry containing the specified adapters.
     *
     * @param adapters The map of classes and adapters.
     */
    public AdapterRegistry(Map<Class, TypeAdapter> adapters)
    {
        this();

        for (Map.Entry<Class, TypeAdapter> entry : adapters.entrySet()) {
            registerAdapter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Creates an empty registry.
     */
    public AdapterRegistry()
    {
        this.adapters = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    /**
     * Returns the map of types and adapters.
     *
     * @return The map of adapters indexed by the supported type.
     */
    public Map<Class, TypeAdapter> getAdapters()
    {
        LinkedHashMap<Class, TypeAdapter> map = new LinkedHashMap<>();

        for (int index = 0, size = classes.size(); index < size; index++) {
            map.put(classes.get(index), adapters.get(index));
        }

        return map;
    }

    /**
     * Registers an adapter for the specified type.
     *
     * This operation overrides any adapter already registered for the
     * specified type.
     *
     * @param type The type supported by the adapter.
     * @param adapter The adapter.
     */
    public void registerAdapter(Class type, TypeAdapter adapter)
    {
        for (int index = 0, size = classes.size(); index < size; index++) {
            Class<?> currentClass = classes.get(index);

            if (currentClass.isAssignableFrom(type)) {
                classes.add(index, type);
                adapters.add(index, adapter);

                return;
            }
        }

        classes.add(type);
        adapters.add(adapter);
    }

    /**
     * Returns the most specified adapter for the specified type.
     *
     * @param type The type which the adapter should support.
     *
     * @return The most specified adapter for the specified type.
     *
     * @throws IllegalArgumentException if no adapter for the specified type is
     * registered.
     */
    public TypeAdapter getAdapter(Class type)
    {
        for (int index = 0, size = classes.size(); index < size; index++) {
            Class<?> currentClass = classes.get(index);

            if (currentClass.isAssignableFrom(type)) {
                return adapters.get(index);
            }
        }

        throw new IllegalArgumentException(String.format(
            "No adapter registered for %s.",
            type
        ));
    }
}
