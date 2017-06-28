package com.phpcommon.phpserializer;

/**
 * Creates instances of Serializer.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class SerializerFactory
{
    /**
     * Creates a serializer with the specified configuration.
     *
     * @param namingStrategy The strategy for naming classes and fields.
     * @param exclusionStrategy The strategy for excluding fields.
     * @param adapterRegistry The registry of type adapters.
     */
    public Serializer create(
        NamingStrategy namingStrategy,
        FieldExclusionStrategy exclusionStrategy,
        AdapterRegistry adapterRegistry
    )
    {
        return new Serializer(
            namingStrategy,
            exclusionStrategy,
            adapterRegistry
        );
    }
}
