package com.phpcommon.phpserializer.adapter;

import java.util.Map;
import com.phpcommon.phpserializer.Context;
import com.phpcommon.phpserializer.TypeAdapter;
import com.phpcommon.phpserializer.Writer;

/**
 * Adapter for handling maps.
 *
 * @param <K> the type of keys maintained by the map.
 * @param <V> the type of mapped values.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class MapAdapter<K, V> implements TypeAdapter<Map<K, V>>
{
    @Override
    public void write(Map<K, V> map, Writer writer, Context context)
    {
        int size = map.size();

        writer.writeArrayStart(size);

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();

            if (key instanceof Integer) {
                writer.writeKey((Integer) key);
            } else {
                writer.writeKey(key.toString());
            }

            context.write(entry.getValue(), writer);
        }

        writer.writeArrayEnd();
    }
}
