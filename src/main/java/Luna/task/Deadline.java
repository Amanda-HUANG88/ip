package Luna.task;

/**
 * Deadline Class
 *
 * Represents a task that needs to be completed by a specific date or time.
 * It is a sub-class of the abstract {@link Task} class.
 */
public class Deadline extends Task {
    protected String date;

    /**
     * Constructs a Deadline object with the given description and deadline date/time.
     * The task is initialized as not done.
     *
     * @param description The textual description of the deadline task.
     * @param date The deadline date or time (the 'by' component).
     */
    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date + ")";
    }
}
