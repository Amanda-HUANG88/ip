package Luna.task;
/**
 * Event Class
 *
 * Represents a task that starts at a specific time and ends at a specific time.
 * It is a sub-class of the abstract {@link Task} class.
 */

public class  Event extends Task {
    protected String start;
    protected String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end= end;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    /**
     * Returns the string representation of the Event task,
     * including its type identifier "[E]" and its start and end times.
     *
     * @return A formatted string representing the Event task status, description, and time range.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
