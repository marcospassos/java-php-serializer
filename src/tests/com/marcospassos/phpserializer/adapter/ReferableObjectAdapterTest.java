package com.marcospassos.phpserializer.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.TypeAdapter;
import com.marcospassos.phpserializer.Writer;
import org.junit.Test;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class ReferableObjectAdapterTest
{
    @Test
    public void writeDelegatesWritingIfReferenceDoesNotExist() throws Exception
    {
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        @SuppressWarnings("unchecked")
        TypeAdapter<Object> decorated =
            (TypeAdapter<Object>) mock(TypeAdapter.class);

        ReferableObjectAdapter<Object> adapter =
            new ReferableObjectAdapter<>(
                decorated
            );

        Object subject = new Object();

        when(context.getReference(subject)).thenReturn(-1);

        adapter.write(subject, writer, context);

        verify(context, times(1)).getReference(subject);
        verify(writer, times(0)).writeObjectReference(-1);
        verify(decorated, times(1)).write(subject, writer, context);
    }

    @Test
    public void writeWritesReferenceIfExists() throws Exception
    {
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);

        @SuppressWarnings("unchecked")
        TypeAdapter<Object> decorated =
            (TypeAdapter<Object>) mock(TypeAdapter.class);

        ReferableObjectAdapter<Object> adapter =
            new ReferableObjectAdapter<>(
                decorated
            );

        Object subject = new Object();

        when(context.getReference(subject)).thenReturn(1);

        adapter.write(subject, writer, context);

        verify(context, times(1)).getReference(subject);
        verify(writer, times(1)).writeObjectReference(1);
        verify(decorated, times(0)).write(subject, writer, context);
    }
}