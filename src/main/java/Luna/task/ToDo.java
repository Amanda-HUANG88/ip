package Luna.task;

/**
 * ToDo Class
 *
 * Represents a simple task that has no deadline or fixed duration.
 * It is a sub-class of the abstract {@link Task} class.
 */

public class ToDo extends Task {
    /**
     * Constructs a ToDo object with the given description.
     * The task is initialized as not done.
     *
     * @param description The textual description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }


    @Override
    /**
     * Returns the string representation of the ToDo task,
     * including its type identifier "[T]".
     *
     * @return A formatted string representing the ToDo task status and description.
     */
    public String toString() {
        return "[T]" + super.toString();
    }
}
