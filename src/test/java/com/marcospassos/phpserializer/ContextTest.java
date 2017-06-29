package com.marcospassos.phpserializer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class ContextTest
{
    FieldExclusionStrategy exclusionStrategy;
    AdapterRegistry adapterRegistry;
    NamingStrategy namingStrategy;
    Context context;

    @Before
    public void setUp() throws Exception
    {
        exclusionStrategy = mock(FieldExclusionStrategy.class);
        adapterRegistry = mock(AdapterRegistry.class);
        namingStrategy = mock(NamingStrategy.class);

        context = new Context(
            adapterRegistry,
            namingStrategy,
            exclusionStrategy
        );
    }

    @Test
    public void getNamingStrategy() throws Exception
    {
        assertSame(namingStrategy, context.getNamingStrategy());
    }

    @Test
    public void getExclusionStrategy() throws Exception
    {
        assertSame(exclusionStrategy, context.getExclusionStrategy());
    }

    @Test
    public void getDepthReturnsCurrentStackSize() throws Exception
    {
        Writer writer = mock(Writer.class);

        final ArrayList<Object> depthStack = new ArrayList<>();
        final Integer[] values = {10, 20, 30};

        TypeAdapter<Integer> adapter = spy(new TypeAdapter<Integer>()
        {
            @Override
            public void write(Integer value, Writer writer, Context context)
            {
                depthStack.add(context.getDepth());

                if (context.getDepth() < 3) {
                    context.write(values[context.getDepth()], writer);
                }
            }
        });

        when(adapterRegistry.getAdapter(Integer.class)).thenReturn(adapter);

        context.write(values[0], writer);

        verify(adapter).write(values[0], writer, context);
        verify(adapter).write(values[1], writer, context);
        verify(adapter).write(values[2], writer, context);

        assertEquals(1, depthStack.get(0));
        assertEquals(2, depthStack.get(1));
        assertEquals(3, depthStack.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getParentFailsIfNoParent() throws Exception
    {
        context.getParent();
    }

    @Test
    public void getParentReturnsObjectThatContainsCurrentObject() throws Exception
    {
        Writer writer = mock(Writer.class);

        final ArrayList<Object> parentStack = new ArrayList<>();
        final Integer[] values = {10, 20, 30};

        TypeAdapter<Integer> adapter = spy(new TypeAdapter<Integer>()
        {
            @Override
            public void write(Integer value, Writer writer, Context context)
            {
                if (context.getDepth() > 1) {
                    parentStack.add(context.getParent());
                }

                if (context.getDepth() < 3) {
                    context.write(values[context.getDepth()], writer);
                }
            }
        });

        when(adapterRegistry.getAdapter(Integer.class)).thenReturn(adapter);

        context.write(values[0], writer);

        verify(adapter).write(values[0], writer, context);
        verify(adapter).write(values[1], writer, context);
        verify(adapter).write(values[2], writer, context);

        assertEquals(2, parentStack.size());
        assertEquals(10, parentStack.get(0));
        assertEquals(20, parentStack.get(1));
    }

    @Test
    public void getTraversalStack() throws Exception
    {
        Writer writer = mock(Writer.class);

        final ArrayList<Object[]> stacks = new ArrayList<>();
        final Integer[] values = {10, 20, 30};

        TypeAdapter<Integer> adapter = spy(new TypeAdapter<Integer>()
        {
            @Override
            public void write(Integer value, Writer writer, Context context)
            {
                stacks.add(context.getTraversalStack().toArray());

                if (context.getDepth() < 3) {
                    context.write(values[context.getDepth()], writer);
                }
            }
        });

        when(adapterRegistry.getAdapter(Integer.class)).thenReturn(adapter);

        context.write(values[0], writer);

        verify(adapter).write(values[0], writer, context);
        verify(adapter).write(values[1], writer, context);
        verify(adapter).write(values[2], writer, context);

        Object[] expected = new Object[]{
            new Object[]{10},
            new Object[]{10, 20},
            new Object[]{10, 20, 30}
        };

        assertArrayEquals(expected, stacks.toArray());
    }

    @Test
    public void writeDelegatesCallToAdapter() throws Exception
    {
        Writer writer = mock(Writer.class);
        TypeAdapter<Integer> adapter = spy(new TypeAdapter<Integer>()
        {
            @Override
            public void write(Integer value, Writer writer, Context context){
            }
        });

        when(adapterRegistry.getAdapter(Integer.class)).thenReturn(adapter);

        context.write(1, writer);

        verify(adapterRegistry).getAdapter(Integer.class);
        verify(adapter).write(1, writer, context);
    }

    @Test
    public void writeWritesNullForNullValues() throws Exception
    {
        Writer writer = mock(Writer.class);
        context.write(null, writer);

        verify(writer).writeNull();
    }

    @Test
    public void getReferenceReturnsMinusOneIfNoReferenceForValueExists() throws Exception
    {
        assertEquals(-1, context.getReference(new Object()));
    }

    @Test
    public void setReferenceStoresAssociatesValueToPointer() throws Exception
    {
        Object a = new Object();
        Object b = new Object();

        context.setReference(1, a);
        context.setReference(2, b);

        assertEquals(1, context.getReference(a));
        assertEquals(2, context.getReference(b));
    }
}