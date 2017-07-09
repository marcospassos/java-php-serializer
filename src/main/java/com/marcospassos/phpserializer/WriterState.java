
package com.marcospassos.phpserializer;

/**
 * Represents a state of the {@link Writer}.
 *
 * @author Marcos Passos
 * @since 1.0
 */
public interface WriterState
{

    /**
     * Checks whether the current state represents a referable value.
     *
     * @return {@code true} if the current state represents a referable value,
     * or {@code false} otherwise.
     */
    boolean isReferable();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState serializableBegin();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState serializableEnd();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState objectBegin();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState property();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState value();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState objectEnd();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState arrayBegin();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState key();

    /**
     * Returns the new state generated as a result of transitions from the
     * current state to the new state.
     *
     * @return WriterState The new state.
     *
     * @throws IllegalStateException if the current state does not allow the
     * transition to the new state.
     */
    WriterState arrayEnd();
}
