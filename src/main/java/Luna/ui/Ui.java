package Luna.ui;

import java.util.Scanner;

/**
 * Ui Class
 *
 * Deals with all interactions with the user, including reading commands
 * and displaying messages.
 */

public class Ui {
    private final Scanner scanner;
    private final String LINE = "\t————————————————————————————————————————";

    /**
     * Constructor for Ui. Initializes the Scanner for user input.
     */

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("\tHello! I'm Luna");
        System.out.println("\tWhat can I do for you?");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a task after it has been added.
     *
     * @param taskString The string representation of the added task.
     * @param taskCount The current total number of tasks.
     */
    public void showTaskAdded(String taskString, int taskCount) {
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + taskString);
        System.out.println("\tNow you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the message for a marked/unmarked task.
     *
     * @param taskString The string representation of the marked/unmarked task.
     * @param isMarked True if the task was marked, false if unmarked.
     */
    public void showMarkUnmarkMessage(String taskString, boolean isMarked) {
        if (isMarked) {
            System.out.println("\tNice! I've marked this task as done:");
        } else {
            System.out.println("\tOK, I've marked this task as not done yet:");
        }
        System.out.println("\t  " + taskString);
    }

    public void showError(String message) {
        System.out.println("\t " + message);
    }

    /**
     * Displays a message when there is an issue loading tasks from file.
     *
     * @param message The error message from the exception.
     */
    public void showLoadingError(String message) {
        System.out.println("\tWarning: " + message);
        System.out.println("\tStarting with a fresh task list.");
    }

    public void showGoodbye() {
        System.out.println("\tBye. Hope to see you again soon!");
    }
}