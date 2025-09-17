import Luna.exception.LunaException;
import Luna.task.Deadline;
import Luna.task.Event;
import Luna.task.Task;
import Luna.task.ToDo;

import java.util.ArrayList;
import java.util.Scanner;

public class Luna {

    private static ArrayList<Task> tasks = new ArrayList<>();

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
                listTasks();
                break;
            case "mark":
                handleMarkUnmark(arguments, true);
                break;
            case "unmark":
                handleMarkUnmark(arguments, false);
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
            case "delete":
                handleDelete(arguments);
                break;
            default:
                System.out.println("\tI'm sorry, but I don't know what that means. Please try again.");
                break;
            }
        } catch (LunaException e) {
            System.out.println("\t" + e.getMessage());
        }
    }

    private static void printLine() {
        System.out.println("\t____________________________________________________________");
    }

    private static void addTodo(String arguments) throws LunaException {
        if (arguments.isEmpty()) {
            throw new LunaException("The description of a todo cannot be empty.");
        }
        tasks.add(new ToDo(arguments));
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(tasks.size() - 1));
        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void addDeadline(String arguments) throws LunaException {
        String[] parts = arguments.split(" /by ");
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new LunaException("The deadline command format is incorrect. Please use: deadline <description> /by <date>");
        }
        tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(tasks.size() - 1));
        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void addEvent(String arguments) throws LunaException {
        String[] parts = arguments.split(" /from ");
        if (parts.length < 2) {
            throw new LunaException("The event command format is incorrect. Please use: event <description> /from <start> /to <end>");
        }
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length < 2 || description.isEmpty() || timeParts[0].isEmpty() || timeParts[1].isEmpty()) {
            throw new LunaException("The event command format is incorrect. Please use: event <description> /from <start> /to <end>");
        }
        tasks.add(new Event(description, timeParts[0].trim(), timeParts[1].trim()));
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + tasks.get(tasks.size() - 1));
        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void handleMarkUnmark(String arguments, boolean isMark) throws LunaException {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                if (isMark) {
                    tasks.get(taskIndex).mark();
                    System.out.println("\tNice! I've marked this task as done:");
                } else {
                    tasks.get(taskIndex).unmark();
                    System.out.println("\tOK, I've marked this task as not done yet:");
                }
                System.out.println("\t  " + tasks.get(taskIndex).toString());
            } else {
                throw new LunaException("That task number is out of range. Please try again.");
            }
        } catch (NumberFormatException e) {
            throw new LunaException("Please provide a valid task number.");
        }
    }

    private static void listTasks() {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
        }
    }

    private static void handleDelete(String arguments) throws LunaException {
        try {
            int index = Integer.parseInt(arguments.trim()) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new LunaException("That task number is out of range. Please try again.");
            }

            Task removedTask = tasks.get(index);
            tasks.remove(index);

            System.out.println("\tNoted. I've removed this task:");
            System.out.println("\t  " + removedTask);
            System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");

        } catch (NumberFormatException e) {
            throw new LunaException("Please provide a valid task number.");
        }
    }
}