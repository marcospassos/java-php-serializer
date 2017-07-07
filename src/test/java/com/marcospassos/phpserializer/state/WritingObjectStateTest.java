package com.marcospassos.phpserializer.state;

import com.marcospassos.phpserializer.WriterState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Marcos Passos
 * @since 1.0
 */
public class WritingObjectStateTest
{

    private WritingObjectState state;
    private WriterState parent;

    @Before
    public void setUp() throws Exception
    {
        parent = mock(WriterState.class);
        state = new WritingObjectState(parent);
    }

    @Test
    public void isReferableReturnsFalse() throws Exception
    {
        assertFalse(state.isReferable());
    }

    @Test(expected = IllegalStateException.class)
    public void valueIsNotAllowed() throws Exception
    {
        state.value();
    }

    @Test(expected = IllegalStateException.class)
    public void objectBeginIsNotAllowed() throws Exception
    {
        state.objectBegin();
    }

    @Test
    public void objectEndIsAllowed() throws Exception
    {
        assertSame(parent, state.objectEnd());
    }

    @Test
    public void propertyIsAllowed() throws Exception
    {
        assertTrue(state.property() instanceof WritingValueState);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayBeginIsNotAllowed() throws Exception
    {
        state.arrayBegin();
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