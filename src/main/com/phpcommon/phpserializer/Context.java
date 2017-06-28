package com.phpcommon.phpserializer;

import java.util.*;

/**
 * Manages the serialization process, including keeping track of references and
 * delegating adapters.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class Context
{
    /**
     * Maps objects to reference indexes.
     */
    private Map<Object, Integer> references;

    /**
     * The adapter registry.
     */
    private AdapterRegistry registry;

    /**
     * The naming strategy.
     */
    private NamingStrategy namingStrategy;

    /**
     * The exclusion strategy.
     */
    private FieldExclusionStrategy exclusionStrategy;

    /**
     * The traversal stack.
     */
    private Stack<Object> traversalStack;

    /**
     * Creates a context.
     *
     * @param registry The adapter registry.
     * @param namingStrategy The naming strategy.
     * @param exclusionStrategy The exclusion strategy.
     */
    public Context(
        AdapterRegistry registry,
        NamingStrategy namingStrategy,
        FieldExclusionStrategy exclusionStrategy
    )
    {
        this.registry = registry;
        this.references = new HashMap<>();
        this.namingStrategy = namingStrategy;
        this.exclusionStrategy = exclusionStrategy;
        this.traversalStack = new Stack<>();
    }

    /**
     * Returns the naming strategy.
     *
     * @return The naming strategy.
     */
    public NamingStrategy getNamingStrategy()
    {
        return namingStrategy;
    }

    /**
     * Returns the exclusion strategy.
     *
     * @return The exclusion strategy.
     */
    public FieldExclusionStrategy getExclusionStrategy()
    {
        return exclusionStrategy;
    }

    /**
     * Returns the traversal depth.
     *
     * The depth is equivalent to the stack size. If the stack is empty returns
     * 0; if the stack contains one element, returns 1 and so on.
     *
     * @return The traversal depth.
     */
    public int getDepth()
    {
        return traversalStack.size();
    }

    /**
     * Returns the parent object that contains the current object.
     *
     * @return The parent object.
     *
     * @throws IndexOutOfBoundsException if the traversal stack is empty or if
     * the object is on the top of the stack.
     */
    public Object getParent()
    {
        return traversalStack.get(traversalStack.size() - 2);
    }

    /**
     * Return the traversal stack.
     *
     * @return The traversal stack.
     */
    public List<Object> getTraversalStack()
    {
        return Collections.unmodifiableList(traversalStack);
    }

    /**
     * Writes the provided value using the specified writer.
     *
     * @param value The value to write.
     * @param writer The writer to write the value with.
     */
    public void write(Object value, Writer writer)
    {
        if (value == null) {
            writer.writeNull();

            return;
        }

        traversalStack.push(value);

        TypeAdapter<Object> adapter = getAdapter(value.getClass());

        adapter.write(value, writer, this);

        traversalStack.pop();
    }

    /**
     * Returns the adapter for the specified type.
     *
     * @param type The type which the adapter should support.
     *
     * @return The adapter for the specified type.
     */
    @SuppressWarnings("unchecked")
    private TypeAdapter<Object> getAdapter(Class<?> type)
    {
        return (TypeAdapter<Object>) registry.getAdapter(type);
    }

    /**
     * Tracks the reference of the specified object.
     *
     * @param pointer The reference index.
     * @param object The value.
     */
    public void setReference(int pointer, Object object)
    {
        references.put(object, pointer);
    }

    /**
     * Returns the index of the reference for the specified object.
     *
     * @param object The object which the reference points to.
     *
     * @return A positive integer representing the index of the reference for
     * the specified object or {@code -1}, if no reference for the specified
     * value exists.
     */
    public int getReference(Object object)
    {
        if (!references.containsKey(object)) {
            return -1;
        }

        return references.get(object);
    }
}
