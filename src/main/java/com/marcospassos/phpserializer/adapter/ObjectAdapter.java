package com.marcospassos.phpserializer.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.FieldExclusionStrategy;
import com.marcospassos.phpserializer.NamingStrategy;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;
import com.marcospassos.phpserializer.util.ReflectionUtils;

/**
 * Adapter for string values.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class ObjectAdapter<T> implements TypeAdapter<T>
{
    @Override
    public void write(T object, Writer writer, Context context)
    {
        Class type = object.getClass();
        Field[] fields = getFields(type, context.getExclusionStrategy());

        NamingStrategy namingStrategy = context.getNamingStrategy();
        String className = namingStrategy.getClassName(type);

        writer.writeObjectStart(className, fields.length);

        writeFields(object, fields, writer, context);

        writer.writeObjectEnd();
    }

    /**
     * Writes fields using the specified writer.
     *
     * @param object The object that owns the field.
     * @param fields The fields to be written.
     * @param writer The writer to use for writing the fields.
     * @param context The serialization context.
     */
    protected void writeFields(
        T object,
        Field[] fields,
        Writer writer,
        Context context
    )
    {
        NamingStrategy namingStrategy = context.getNamingStrategy();

        for (Field field : fields) {
            String name = namingStrategy.getFieldName(field);
            Class<?> declaringClass = field.getDeclaringClass();
            String fieldClass = namingStrategy.getClassName(declaringClass);

            writer.writeProperty(name, fieldClass, getModifiers(field));
            context.write(ReflectionUtils.getValue(object, field), writer);
        }
    }

    /**
     * Returns the access modifier for the specified field.
     *
     * This method can be overridden to implement custom logic for porting
     * property modifiers from Java to PHP.
     *
     * @param field The field to define the access modifier.
     *
     * @return The modifier.
     *
     * @see java.lang.reflect.Modifier
     */
    protected int getModifiers(Field field)
    {
        return field.getModifiers();
    }

    /**
     * Returns the list of fields of a class filtered according to the
     * specified exclusion strategy.
     *
     * @param type The class to collect the fields from.
     * @param exclusionStrategy The strategy for filtering fields.
     *
     * @return The modifier.
     *
     * @see java.lang.reflect.Modifier
     */
    protected Field[] getFields(
        Class type,
        FieldExclusionStrategy exclusionStrategy
    )
    {
        List<Field> filtered = new ArrayList<>();

        for (Field field : ReflectionUtils.getFields(type)) {
            if (exclusionStrategy.shouldSkipField(field)) {
                continue;
            }

            filtered.add(field);
        }

        return filtered.toArray(new Field[0]);
    }
}
