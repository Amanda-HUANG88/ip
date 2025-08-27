import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
        System.out.println("\t————————————————————————————————————————");
        System.out.println("\tHello! I'm Luna");
        System.out.println("\tWhat can I do for you?");
        System.out.println("\t————————————————————————————————————————");

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            System.out.println("\t————————————————————————————————————————");
            if (input.equals("bye")) {
                System.out.println("\tBye. Hope to see you again soon!");
                System.out.println("\t————————————————————————————————————————");
                break;
            } else if (input.equals("list")) {
                System.out.println("\tHere are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("\t" + (i + 1) + ". " + tasks[i].toString());
                }
            } else if(input.startsWith("mark ")){
                try {
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        tasks[taskIndex].mark();
                        System.out.println("\tNice! I've marked this task as done:");
                        System.out.println("\t  " + tasks[taskIndex].toString());
                    } else {
                        System.out.println("\tSorry, that task number is out of range. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\tPlease provide a valid task number to mark.");
                }
            } else if(input.startsWith("unmark ")){
                try {
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        tasks[taskIndex].unmark();
                        System.out.println("\tOK, I've marked this task as not done yet:");
                        System.out.println("\t  " + tasks[taskIndex].toString());
                    } else {
                        System.out.println("\tSorry, that task number is out of range. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\tPlease provide a valid task number to unmark.");
                }
            }else{
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("\tadded: " + input);
            }
            System.out.println("\t————————————————————————————————————————");
        }
        scanner.close();
    }
}

