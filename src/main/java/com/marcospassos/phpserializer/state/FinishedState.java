
package com.marcospassos.phpserializer.state;

/**
 * Represents the final state of the writing process.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public class FinishedState extends AbstractState
{
    @Override
    public boolean isReferable()
    {
        return false;
    }
}
