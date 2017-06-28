package com.phpcommon.phpserializer;

/**
 * Writes Java objects in PHP's Serialization format.
 *
 * @param <T> The type of object supported.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public interface TypeAdapter<T>
{
    /**
     * Encodes the specified value in PHP's Serialization format.
     *
     * @param value The value to write.
     * @param writer The writer.
     * @param context The serialization context.
     */
    void write(T value, Writer writer, Context context);
}
