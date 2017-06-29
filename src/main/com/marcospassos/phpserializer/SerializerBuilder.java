package com.marcospassos.phpserializer;

import com.marcospassos.phpserializer.adapter.*;
import com.marcospassos.phpserializer.exclusion.DisjunctionExclusionStrategy;
import com.marcospassos.phpserializer.exclusion.NoExclusionStrategy;
import com.marcospassos.phpserializer.naming.PsrNamingStrategy;

import java.util.*;

/**
 * Provides a friendly API for creating instances of {@link Serializer}.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class SerializerBuilder
{
    /**
     * The naming strategy.
     */
    private NamingStrategy namingStrategy;

    /**
     * The exclusion strategies.
     */
    private List<FieldExclusionStrategy> exclusionStrategies;

    /**
     * The map of adapters.
     */
    private Map<Class, TypeAdapter> adapterMap;

    /**
     * The serializer factory.
     */
    private SerializerFactory factory;

    /**
     * Creates a builder with the specified factory.
     *
     * @param factory The factory to create the serializer.
     */
    public SerializerBuilder(SerializerFactory factory)
    {
        this.adapterMap = new HashMap<>();
        this.exclusionStrategies = new ArrayList<>();
        this.factory = factory;
    }

    /**
     * Creates a builder using an internal factory.
     */
    public SerializerBuilder()
    {
        this(new SerializerFactory());
    }

    /**
     * Creates a {@link Serializer} instance based on the current configuration.
     *
     * This method is free of side-effects to this GsonBuilder instance and
     * hence can be called multiple times.
     *
     * @return An instance of {@link Serializer} configured with the options
     * currently set in this builder.
     */
    public Serializer build()
    {
        AdapterRegistry adapterRegistry = getAdapterRegistry();
        FieldExclusionStrategy exclusionStrategy = getExclusionStrategy();
        NamingStrategy namingStrategy = getNamingStrategy();

        return factory.create(
            namingStrategy,
            exclusionStrategy,
            adapterRegistry
        );
    }

    /**
     * Configures the serializer to apply a specific naming policy strategy to
     * an objects during serialization.
     *
     * @param strategy The naming strategy to apply to objects.
     *
     * @return The current builder.
     */
    public SerializerBuilder setNamingStrategy(NamingStrategy strategy)
    {
        this.namingStrategy = strategy;

        return this;
    }

    /**
     * Configures the serializer to apply the specified in exclusion strategy
     * during serialization.
     *
     * If this method is invoked numerous times with different exclusion
     * strategy objects then the exclusion strategies that were added will be
     * applied as a disjunction rule. This means that if one of the added
     * exclusion strategies suggests that a field (or class) should be skipped
     * then that field (or object) is skipped during its deserialization.
     *
     * @param strategy The exclusion strategy.
     *
     * @return The current builder.
     */
    public SerializerBuilder addExclusionStrategy(FieldExclusionStrategy strategy)
    {
        if (!this.exclusionStrategies.contains(strategy)) {
            this.exclusionStrategies.add(strategy);
        }

        return this;
    }

    /**
     * Registers the provided adapter for the specified type.
     *
     * This registers the type specified and all subclasses. For example,
     * an adapter registered for {@code Number.class} will be also applied to
     * {@code Integer.class}.
     *
     * If a type adapter was previously registered for the specified type, it
     * is overwritten.
     *
     * @param type The base class or interface supported by the adapter.
     * @param adapter The type adapter.
     *
     * @return The current builder.
     */
    public SerializerBuilder registerAdapter(Class type, TypeAdapter adapter)
    {
        adapterMap.put(type, adapter);

        return this;
    }

    /**
     * Registers all builtin adapters.
     *
     * @return The current builder.
     */
    public SerializerBuilder registerBuiltinAdapters()
    {
        ArrayAdapter arrayAdapter = new ArrayAdapter();

        registerAdapter(Object[].class, arrayAdapter);
        registerAdapter(int[].class, arrayAdapter);
        registerAdapter(float[].class, arrayAdapter);
        registerAdapter(byte[].class, arrayAdapter);
        registerAdapter(boolean[].class, arrayAdapter);
        registerAdapter(long[].class, arrayAdapter);
        registerAdapter(double[].class, arrayAdapter);
        registerAdapter(char[].class, arrayAdapter);
        registerAdapter(short[].class, arrayAdapter);
        registerAdapter(Map.class, new MapAdapter());
        registerAdapter(List.class, new ListAdapter());
        registerAdapter(Boolean.class, new BooleanAdapter());
        registerAdapter(Integer.class, new IntegerAdapter());
        registerAdapter(String.class, new StringAdapter());
        registerAdapter(Object.class, new ReferableObjectAdapter<>(
            new ObjectAdapter<>()
        ));

        return this;
    }

    /**
     * Returns the configured naming strategy or the default one.
     *
     * @return The naming strategy.
     */
    private NamingStrategy getNamingStrategy()
    {
        if (namingStrategy == null) {
            return new PsrNamingStrategy();
        }

        return namingStrategy;
    }

    /**
     * Returns the adapter registry containing the configured adapters.
     *
     * @return The adapter registry.
     */
    private AdapterRegistry getAdapterRegistry()
    {
        Map<Class, TypeAdapter> adapterMap = this.adapterMap;

        if (adapterMap.isEmpty()) {
            registerBuiltinAdapters();

            this.adapterMap = new HashMap<>();
        }

        return new AdapterRegistry(adapterMap);
    }

    /**
     * Returns the configured the exclusion strategy or the default one.
     *
     * @return The exclusion strategy.
     */
    private FieldExclusionStrategy getExclusionStrategy()
    {
        if (exclusionStrategies.isEmpty()) {
            return new NoExclusionStrategy();
        }

        if (exclusionStrategies.size() > 1) {
            return new DisjunctionExclusionStrategy(
                exclusionStrategies.toArray(new FieldExclusionStrategy[0])
            );
        }

        return exclusionStrategies.get(0);
    }
}
