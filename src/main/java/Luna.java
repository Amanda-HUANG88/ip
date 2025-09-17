import Luna.exception.LunaException;
import Luna.task.Deadline;
import Luna.task.Event;
import Luna.task.Task;
import Luna.task.ToDo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Luna {

    private static final String FILE_PATH = "./data/luna.txt";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasksFromFile();

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
        saveTasksToFile();
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
        saveTasksToFile();
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
        saveTasksToFile();
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
                saveTasksToFile();
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
            saveTasksToFile();

        } catch (NumberFormatException e) {
            throw new LunaException("Please provide a valid task number.");
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(" \\| ");
                    String taskType = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task = null;

                    switch (taskType) {
                    case "T":
                        task = new ToDo(description);
                        break;
                    case "D":
                        String by = parts[3];
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
                        task = new Event(description, from, to);
                        break;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.mark();
                        }
                        tasks.add(task);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Warning: Corrupted task data found in file. Skipping line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("\tNo saved tasks found. Starting with a fresh list.");
        }
    }

    private static void saveTasksToFile() {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                String taskString = "";
                String isDone = task.getStatus();

                if (task instanceof ToDo) {
                    taskString = String.format("T | %s | %s\n", isDone, task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    taskString = String.format("D | %s | %s | %s\n", isDone, deadline.getDescription(), deadline.getDate());
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    taskString = String.format("E | %s | %s | %s | %s\n", isDone, event.getDescription(), event.getStart(), event.getEnd());
                }

                fw.write(taskString);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}