package Luna.task;

/**
 * Task Class
 *
 * Represents a generic task. This is the abstract base class for all
 * specific task types (ToDo, Deadline, Event). It manages the task's
 * description and completion status.
 */

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task object with the given description.
     * The task is always initialized as not done.
     *
     * @param description The textual description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Gets the completion status symbol for display or saving.
     *
     * @return "X" if the task is done, and a space (" ") if it is not done.
     */
    public String getStatus() {
        return (isDone ? "X" : " "); 
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the Task,
     * including its status (e.g., "[ ] read book").
     *
     * @return A formatted string representing the task's status and description.
     */
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
