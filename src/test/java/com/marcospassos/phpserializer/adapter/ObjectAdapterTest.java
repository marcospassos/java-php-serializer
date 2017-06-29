package com.marcospassos.phpserializer.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.marcospassos.phpserializer.Context;
import com.marcospassos.phpserializer.FieldExclusionStrategy;
import com.marcospassos.phpserializer.NamingStrategy;
import com.marcospassos.phpserializer.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ObjectAdapterTest
{
    private class Subject {
        private int privateField;
        protected int protectedField;
        public int publicField;
        public int excludedField;

        public Subject(
            int privateField,
            int protectedField,
            int publicField,
            int excludedField
        )
        {
            this.privateField = privateField;
            this.protectedField = protectedField;
            this.publicField = publicField;
            this.excludedField = excludedField;
        }
    }

    @Test
    public void write() throws Exception
    {
        ObjectAdapter<Object> adapter = new ObjectAdapter<>();
        Writer writer = mock(Writer.class);
        Context context = mock(Context.class);
        FieldExclusionStrategy exclusionStrategy = mock(FieldExclusionStrategy.class);
        NamingStrategy namingStrategy = mock(NamingStrategy.class);

        Field privateField = Subject.class.getDeclaredField("privateField");
        Field protectedField = Subject.class.getDeclaredField("protectedField");
        Field publicField = Subject.class.getDeclaredField("publicField");

        when(context.getExclusionStrategy()).thenReturn(exclusionStrategy);
        when(context.getNamingStrategy()).thenReturn(namingStrategy);

        when(namingStrategy.getClassName(Subject.class)).thenReturn("Subject");
        when(namingStrategy.getFieldName(privateField)).thenReturn("PrivateField");
        when(namingStrategy.getFieldName(protectedField)).thenReturn("ProtectedField");
        when(namingStrategy.getFieldName(publicField)).thenReturn("PublicField");

        when(exclusionStrategy.shouldSkipField(any(Field.class)))
            .thenAnswer(new Answer<Boolean>() {
                public Boolean answer(InvocationOnMock invocation) {
                    Field field = invocation.getArgument(0);

                    return field.getName().equals("excludedField");
                }
            });

        Subject subject = new Subject(1, 2, 3, 4);

        adapter.write(subject, writer, context);

        InOrder order = inOrder(writer, context);

        order.verify(writer).writeObjectStart("Subject", 3);

        order.verify(writer).writeProperty(
            "PrivateField",
            "Subject",
            Modifier.PRIVATE
        );

        order.verify(context).write(1, writer);

        order.verify(writer).writeProperty(
            "ProtectedField",
            "Subject",
            Modifier.PROTECTED
        );

        order.verify(context).write(2, writer);

        order.verify(writer).writeProperty(
            "PublicField",
            "Subject",
            Modifier.PUBLIC
        );

        order.verify(context).write(3, writer);

        order.verify(writer).writeObjectEnd();
    }
}