package Luna.storage;

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

/**
 * Storage Class
 *
 * Deals with reading tasks from the file and writing tasks to the file.
 */

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws LunaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            parentDir.mkdir(); // Create directory if it doesn't exist
        }

        if (!file.exists()) {
            // File doesn't exist, which is fine, just means no saved tasks
            System.out.println("\tNo saved tasks file found.");
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Task task = parseTaskFromFileLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Log the corrupted line and continue loading others
                    System.out.println("\tWarning: Corrupted task data found in file. Skipping line: " + line);
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new LunaException("Error loading tasks from file: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the save file into a Task object.
     * Format: TYPE | IS_DONE | DESCRIPTION [| TIME_INFO...]
     */
    private Task parseTaskFromFileLine(String line) throws LunaException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new LunaException("Incomplete task data.");
        }

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;

        switch (taskType) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            if (parts.length < 4) throw new LunaException("Incomplete deadline data.");
            String by = parts[3];
            task = new Deadline(description, by);
            break;
        case "E":
            if (parts.length < 5) throw new LunaException("Incomplete event data.");
            String from = parts[3];
            String to = parts[4];
            task = new Event(description, from, to);
            break;
        default:
            throw new LunaException("Unknown task type: " + taskType);
        }

        if (isDone) {
            task.mark();
        }
        return task;
    }

    public void save(ArrayList<Task> tasks) {
        try (FileWriter fw = new FileWriter(filePath)) {
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
            System.out.println("\tError saving tasks to file: " + e.getMessage());
        }
    }
}