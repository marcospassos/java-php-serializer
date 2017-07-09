package com.marcospassos.phpserializer.state;

import com.marcospassos.phpserializer.WriterState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class WritingValueStateTest
{
    private WriterState parent;
    private WritingValueState state;

    @Before
    public void setUp() throws Exception
    {
        parent = mock(WriterState.class);
        state = new WritingValueState(parent);
    }

    @Test
    public void endStateIsParentByDefault() throws Exception
    {
        assertTrue(new WritingValueState().value() instanceof FinishedState);
    }

    @Test
    public void isReferableReturnsTrue() throws Exception
    {
        assertTrue(state.isReferable());
    }

    @Test
    public void valueIsAllowed() throws Exception
    {
        assertSame(parent, state.value());
    }

    @Test
    public void objectBeginIsAllowed() throws Exception
    {
        assertTrue(state.objectBegin() instanceof WritingObjectState);
    }

    @Test(expected = IllegalStateException.class)
    public void objectEndIsNotAllowed() throws Exception
    {
        state.objectEnd();
    }

    @Test
    public void serializableObjectBeginIsAllowed() throws Exception
    {
        state.serializableBegin();
    }

    @Test(expected = IllegalStateException.class)
    public void serializableObjectEndIsNotAllowed() throws Exception
    {
        state.serializableEnd();
    }

    @Test(expected = IllegalStateException.class)
    public void propertyIsNotAllowed() throws Exception
    {
        state.property();
    }

    @Test
    public void arrayBeginIsAllowed() throws Exception
    {
        assertTrue(state.arrayBegin() instanceof WritingArrayState);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayEndIsNotAllowed() throws Exception
    {
        state.arrayEnd();
    }

    @Test(expected = IllegalStateException.class)
    public void keyIsNotAllowed() throws Exception
    {
        state.key();
    }
}