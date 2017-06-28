package com.phpcommon.phpserializer;

/**
 * Serializes Java objects as PHP serialization format.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class Serializer
{
    /**
     * The naming strategy.
     */
    NamingStrategy namingStrategy;

    /**
     * The field exclusion strategy.
     */
    FieldExclusionStrategy exclusionStrategy;

    /**
     * The adapter registry.
     */
    AdapterRegistry adapterRegistry;

    /**
     * Creates a serializer with the specified configuration.
     *
     * @param namingStrategy The strategy for naming classes and fields.
     * @param exclusionStrategy The strategy for excluding fields.
     * @param adapterRegistry The registry of type adapters.
     */
    public Serializer(
        NamingStrategy namingStrategy,
        FieldExclusionStrategy exclusionStrategy,
        AdapterRegistry adapterRegistry
    )
    {
        this.namingStrategy = namingStrategy;
        this.exclusionStrategy = exclusionStrategy;
        this.adapterRegistry = adapterRegistry;
    }

    /**
     * Serializes the specified object as PHP serialization format.
     *
     * @param object The object to serialize.
     *
     * @return The serialized data.
     */
    public String serialize(Object object)
    {
        Writer writer = new Writer();
        Context context = createContext();

        context.write(object, writer);

        return writer.getResult();
    }

    /**
     * Creates the serialization context based on the current configuration.
     *
     * @return The serialization context based on the current configuration.
     */
    private Context createContext()
    {
        return new Context(adapterRegistry, namingStrategy, exclusionStrategy);
    }
}
