package com.marcospassos.phpserializer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcospassos.phpserializer.exclusion.DisjunctionExclusionStrategy;
import com.marcospassos.phpserializer.exclusion.NoExclusionStrategy;
import com.marcospassos.phpserializer.naming.PsrNamingStrategy;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class SerializerBuilderTest
{
    private class Foo
    {
        public int field;
    }

    @Test
    public void build() throws Exception
    {
        SerializerFactory factory = mock(SerializerFactory.class);
        FieldExclusionStrategy exclusionStrategy = mock(FieldExclusionStrategy.class);
        NamingStrategy namingStrategy = mock(NamingStrategy.class);
        TypeAdapter adapter = mock(TypeAdapter.class);

        new SerializerBuilder(factory)
            .registerBuiltinAdapters()
            .addExclusionStrategy(exclusionStrategy)
            .registerAdapter(Foo.class, adapter)
            .setNamingStrategy(namingStrategy)
            .build()
        ;

        ArgumentCaptor<FieldExclusionStrategy> exclusionStrategyArgument =
            ArgumentCaptor.forClass(FieldExclusionStrategy.class);

        ArgumentCaptor<AdapterRegistry> adapterRegistryArgument =
            ArgumentCaptor.forClass(AdapterRegistry.class);

        ArgumentCaptor<NamingStrategy> namingStrategyArgument =
            ArgumentCaptor.forClass(NamingStrategy.class);

        verify(factory).create(
            namingStrategyArgument.capture(),
            exclusionStrategyArgument.capture(),
            adapterRegistryArgument.capture()
        );

        AdapterRegistry registry = adapterRegistryArgument.getValue();
        Map<Class, TypeAdapter> adapters = registry.getAdapters();

        assertTrue(adapters.size() > 1);
        assertTrue(adapters.containsKey(Foo.class));
        assertTrue(adapters.containsValue(adapter));

        assertSame(namingStrategy, namingStrategyArgument.getValue());
        assertSame(exclusionStrategy, exclusionStrategyArgument.getValue());
    }

    @Test
    public void builderInstantiatesFactoryIfNotSpecified() throws Exception
    {
        new SerializerBuilder().build();
    }

    @Test
    public void exclusionStrategiesAreAggregatedIntoDisjunction()
        throws Exception
    {
        SerializerFactory factory = mock(SerializerFactory.class);
        FieldExclusionStrategy first = mock(FieldExclusionStrategy.class);
        FieldExclusionStrategy second = mock(FieldExclusionStrategy.class);
        Field field = Foo.class.getField("field");

        when(first.shouldSkipField(field)).thenReturn(false);
        when(second.shouldSkipField(field)).thenReturn(false);

        new SerializerBuilder(factory)
            .registerBuiltinAdapters()
            .addExclusionStrategy(first)
            .addExclusionStrategy(second)
            .build()
        ;

        ArgumentCaptor<FieldExclusionStrategy> exclusionStrategyArgument =
            ArgumentCaptor.forClass(FieldExclusionStrategy.class);

        verify(factory).create(
            any(NamingStrategy.class),
            exclusionStrategyArgument.capture(),
            any(AdapterRegistry.class)
        );

        FieldExclusionStrategy disjunction = exclusionStrategyArgument
            .getValue();
        assertTrue(disjunction instanceof DisjunctionExclusionStrategy);

        assertFalse(disjunction.shouldSkipField(field));

        verify(first).shouldSkipField(field);
        verify(second).shouldSkipField(field);
    }

    @Test
    public void namingStrategyIsPsrByDefault() throws Exception
    {
        SerializerFactory factory = mock(SerializerFactory.class);

        new SerializerBuilder(factory).build();

        ArgumentCaptor<NamingStrategy> namingStrategyArgument =
            ArgumentCaptor.forClass(NamingStrategy.class);

        verify(factory).create(
            namingStrategyArgument.capture(),
            any(FieldExclusionStrategy.class),
            any(AdapterRegistry.class)
        );

        NamingStrategy strategy = namingStrategyArgument.getValue();

        assertTrue(strategy instanceof PsrNamingStrategy);
    }

    @Test
    public void exclusionStrategyIsNoExclusionByDefault() throws Exception
    {
        SerializerFactory factory = mock(SerializerFactory.class);

        new SerializerBuilder(factory).build();

        ArgumentCaptor<FieldExclusionStrategy> exclusionStrategyArgument =
            ArgumentCaptor.forClass(FieldExclusionStrategy.class);

        verify(factory).create(
            any(NamingStrategy.class),
            exclusionStrategyArgument.capture(),
            any(AdapterRegistry.class)
        );

        FieldExclusionStrategy strategy = exclusionStrategyArgument.getValue();

        assertTrue(strategy instanceof NoExclusionStrategy);
    }

    @Test
    public void builderRegistersBuiltinAdaptersIfNoAdapterIsRegistered()
        throws Exception
    {
        SerializerFactory factory = mock(SerializerFactory.class);

        new SerializerBuilder(factory).build();

        ArgumentCaptor<AdapterRegistry> adapterRegistryArgument =
            ArgumentCaptor.forClass(AdapterRegistry.class);

        verify(factory).create(
            any(NamingStrategy.class),
            any(FieldExclusionStrategy.class),
            adapterRegistryArgument.capture()
        );

        AdapterRegistry registry = adapterRegistryArgument.getValue();
        Map<Class, TypeAdapter> adapters = registry.getAdapters();

        assertFalse(adapters.isEmpty());
    }
}