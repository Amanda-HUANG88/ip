package Luna.task;

import Luna.exception.LunaException;
import Luna.ui.Ui;

import java.util.ArrayList;

/**
 * TaskList Class
 *
 * Contains the list of tasks and provides operations to manipulate them
 * (e.g., add, mark, list).
 */

public class TaskList {
    private final ArrayList<Task> tasks;
    private final Ui ui = new Ui();

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a new task based on the command type and arguments.
     *
     * @param commandType The type of task ("todo", "deadline", "event").
     * @param arguments The raw arguments for the task.
     * @throws LunaException If the task format is invalid.
     */
    public void addTask(String commandType, String arguments) throws LunaException {
        Task newTask;
        String trimmedArgs = arguments.trim();

        switch (commandType) {
        case "todo":
            if (trimmedArgs.isEmpty()) {
                throw new LunaException("The description for a 'todo' cannot be empty. Please provide a task description.");
            }
            newTask = new ToDo(trimmedArgs);
            break;
        case "deadline":
            String[] deadlineParts = arguments.split(" /by ");
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                throw new LunaException("Invalid deadline format. Please use: 'deadline [task] /by [date/time]'.");
            }
            newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
            break;
        case "event":
            String[] eventParts = arguments.split(" /from ");
            if (eventParts.length < 2) {
                throw new LunaException("Invalid event format. Missing '/from' or '/to'. " +
                        "Please use: 'event [task] /from [start] /to [end]'.");
            }
            String description = eventParts[0];
            String[] timeParts = eventParts[1].split(" /to ");
            if (timeParts.length < 2 || description.trim().isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                throw new LunaException("Invalid event format. " +
                        "Please ensure all parts are filled: 'event [task] /from [start] /to [end]'.");
            }
            newTask = new Event(description, timeParts[0], timeParts[1]);
            break;
        default:
            throw new LunaException("Unknown task type for addition: " + commandType);
        }

        tasks.add(newTask);
        ui.showTaskAdded(newTask.toString(), tasks.size());
    }

    public void listTasks(Ui ui) {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
        }
    }

    /**
     * Marks or unmarks a task as done/not done.
     *
     * @param index The 0-based index of the task in the list.
     * @param isMark True to mark the task, false to unmark it.
     * @throws LunaException If the index is out of range.
     */
    public void markUnmarkTask(int index, boolean isMark) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("Sorry, that task number is out of range. Please try again.");
        }

        Task task = tasks.get(index);
        if (isMark) {
            task.mark();
        } else {
            task.unmark();
        }
        ui.showMarkUnmarkMessage(task.toString(), isMark);
    }

    public void deleteTask(int index) throws LunaException {
        if (index < 0 || index >= tasks.size()) {
            throw new LunaException("Sorry, that task number is out of range. Please try again.");
        }

        Task taskToDelete = tasks.get(index);
        tasks.remove(index);

        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t  " + taskToDelete.toString());
        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
    }
}