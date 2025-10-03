import Luna.ui.Ui;
import Luna.storage.Storage;
import Luna.parser.Parser;
import Luna.task.TaskList;
import Luna.exception.LunaException;

/**
 * Luna Class
 *
 * Luna is a simple command-line task management chatbot application
 * that allows users to add, list, mark, and unmark tasks.
 * It also supports saving and loading tasks to/from a local file.
 */

public class Luna {

    private static final String FILE_PATH = "./data/luna.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor for Luna.
     * Initializes the UI, Storage, and attempts to load tasks.
     */
    public Luna() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (LunaException e) {
            ui.showLoadingError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the main logic of the application.
     * Handles the user interaction loop until the 'bye' command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                String[] parsedCommand = Parser.parse(fullCommand);
                String commandType = parsedCommand[0];
                String arguments = parsedCommand.length > 1 ? parsedCommand[1] : "";

                executeCommand(commandType, arguments);

                isExit = commandType.equals("bye");

            } catch (LunaException e) {
                ui.showError("OOPS!!! " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Executes the command based on the command type and arguments.
     *
     * @param commandType The type of command (e.g., "list", "todo", "bye").
     * @param arguments The arguments associated with the command.
     * @throws LunaException If the command execution fails (e.g., invalid format).
     */
    private void executeCommand(String commandType, String arguments) throws LunaException {
        switch (commandType) {
        case "list":
            tasks.listTasks(ui);
            break;
        case "mark":
        case "unmark":
            handleMarkUnmark(commandType, arguments);
            break;
        case "todo":
        case "deadline":
        case "event":
            tasks.addTask(commandType, arguments);
            storage.save(tasks.getTasks());
            break;
        case "delete":
            handleDeleteTask(arguments);
            storage.save(tasks.getTasks());
            break;
        case "find":
            handleFindTask(arguments);
            break;
        case "bye":
            ui.showGoodbye();
            break;
        default:
            throw new LunaException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Handles the 'mark' and 'unmark' commands.
     *
     * @param commandType Must be "mark" or "unmark".
     * @param arguments The task index to mark/unmark.
     */
    private void handleMarkUnmark(String commandType, String arguments) {
        if (arguments.isEmpty()) {
            ui.showError("Please provide a task number to " + commandType + ".");
            return;
        }
        try {
            // Task index in the list is 1-based, array index is 0-based
            int taskIndex = Integer.parseInt(arguments) - 1;
            tasks.markUnmarkTask(taskIndex, commandType.equals("mark"));
            storage.save(tasks.getTasks()); // Save changes after marking/unmarking
        } catch (NumberFormatException e) {
            ui.showError("Please provide a valid task number.");
        } catch (LunaException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleDeleteTask(String arguments) {
        if (arguments.isEmpty()) {
            ui.showError("Please provide a task number to delete.");
            return;
        }
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            tasks.deleteTask(taskIndex);
        } catch (NumberFormatException e) {
            ui.showError("Please provide a valid task number.");
        } catch (LunaException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles the 'find' command.
     *
     * @param arguments The keyword to search for.
     */
    private void handleFindTask(String arguments) throws LunaException {
        if (arguments.trim().isEmpty()) {
            ui.showError("Please provide a keyword to search for.");
            return;
        }
        tasks.findTasks(arguments, ui);
    }

    /**
     * Main method to start the Luna application.
     */
    public static void main(String[] args) {
        new Luna().run();
    }
}