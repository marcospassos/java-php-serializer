
package com.marcospassos.phpserializer.state;

import com.marcospassos.phpserializer.WriterState;

/**
 * An abstract state that implements the operations in such a way that all
 * methods raise an {@link IllegalStateException} by default.
 *
 * Child classes must overwrite the operations appropriately to return the
 * object that represents the new state.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public abstract class AbstractState implements WriterState
{
    public WriterState objectBegin()
    {
        throw new IllegalStateException();
    }

    public WriterState property()
    {
        throw new IllegalStateException();
    }

    public WriterState value()
    {
        throw new IllegalStateException();
    }

    public WriterState objectEnd()
    {
        throw new IllegalStateException();
    }

    public WriterState arrayBegin()
    {
        throw new IllegalStateException();
    }

    public WriterState key()
    {
        throw new IllegalStateException();
    }

    public WriterState arrayEnd()
    {
        throw new IllegalStateException();
    }
}
