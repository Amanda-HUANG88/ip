import java.util.Scanner;

public class Luna {

    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {
        printLine();
        System.out.println("\tHello! I'm Luna");
        System.out.println("\tWhat can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            printLine();
            if (input.equals("bye")) {
                System.out.println("\tBye. Hope to see you again soon!");
                printLine();
                break;
            }
            handleCommand(input);
            printLine();
        }
        scanner.close();
    }

    private static void handleCommand(String input) {
        try {
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String arguments = (parts.length > 1) ? parts[1] : "";

            switch (command) {
            case "list":
                listTasks(tasks, taskCount);
                break;
            case "mark":
                if (arguments.isEmpty()) {
                    throw new LunaException("Please provide a task number to mark.");
                }
                handleMarkUnmark("mark " + arguments, true);
                break;
            case "unmark":
                if (arguments.isEmpty()) {
                    throw new LunaException("Please provide a task number to unmark.");
                }
                handleMarkUnmark("unmark " + arguments, false);
                break;
            case "todo":
                addTodo(arguments);
                break;
            case "deadline":
                addDeadline(arguments);
                break;
            case "event":
                addEvent(arguments);
                break;
            default:
                throw new LunaException("I'm sorry, but I don't know what that means :-(");
            }
        } catch (LunaException e) {
            System.out.println("\t OOPS!!! " + e.getMessage());
        }
    }


    private static void addTodo(String description) throws LunaException {
        if (description.trim().isEmpty()) {
            throw new LunaException("The description for a 'todo' cannot be empty. Please provide a task description.");
        }
        tasks[taskCount] = new ToDo(description);
        taskCount++;
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks[taskCount - 1].toString());
        System.out.println("\tNow you have " + taskCount + " tasks in the list.");
    }

    private static void addDeadline(String input) throws LunaException{
        String[] parts = input.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new LunaException("Invalid deadline format. Please use: 'deadline [task] /by [date/time]'.");
        }
        String description = parts[0];
        String date = parts[1];
        tasks[taskCount] = new Deadline(description, date);
        taskCount++;
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks[taskCount - 1].toString());
        System.out.println("\tNow you have " + taskCount + " tasks in the list.");
    }

    private static void addEvent(String input) throws LunaException {
        String[] parts = input.split(" /from ");
        if (parts.length < 2) {
            throw new LunaException("Invalid event format. Missing '/from' or '/to'. " +
                    "Please use: 'event [task] /from [start] /to [end]'.");
        }
        String description = parts[0];
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length < 2 || description.trim().isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new LunaException("Invalid event format. " +
                    "Please ensure all parts are filled: 'event [task] /from [start] /to [end]'.");
        }
        String start = timeParts[0];
        String end = timeParts[1];
        tasks[taskCount] = new Event(description, start, end);
        taskCount++;
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks[taskCount - 1].toString());
        System.out.println("\tNow you have " + taskCount + " tasks in the list.");
    }

    private static void handleMarkUnmark(String input, boolean isMark) {
        try {
            int taskIndex = Integer.parseInt(input.substring(isMark ? 5 : 7)) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                if (isMark) {
                    tasks[taskIndex].mark();
                    System.out.println("\tNice! I've marked this task as done:");
                } else {
                    tasks[taskIndex].unmark();
                    System.out.println("\tOK, I've marked this task as not done yet:");
                }
                System.out.println("\t  " + tasks[taskIndex].toString());
            } else {
                System.out.println("\tSorry, that task number is out of range. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\tPlease provide a valid task number.");
        }
    }

    private static void listTasks(Task[] tasks, int taskCount) {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks[i].toString());
        }
    }

    private static void printLine() {
        System.out.println("\t————————————————————————————————————————");
    }
}

class LunaException extends Exception {
    public LunaException(String message) {
        super(message);
    }
}

