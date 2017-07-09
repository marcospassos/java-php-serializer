
package com.marcospassos.phpserializer.state;

import com.marcospassos.phpserializer.WriterState;

/**
 * Represents the state of the writer while writing an object.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class WritingSerializableObjectState extends AbstractState
{
    /**
     * The parent state.
     */
    WriterState parent;

    /**
     * Creates a new state from the specified parent state.
     *
     * @param parent The parent state.
     */
    public WritingSerializableObjectState(WriterState parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean isReferable()
    {
        return false;
    }

    @Override
    public WriterState serializableEnd()
    {
        return parent;
    }
}
