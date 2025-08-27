import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
        System.out.println("\t————————————————————————————————————————");
        System.out.println("\tHello! I'm Luna");
        System.out.println("\tWhat can I do for you?");
        System.out.println("\t————————————————————————————————————————");

        String[] tasks = new String[100];
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("\t" + (i + 1) + ". " + tasks[i]);
                    }
                }else{
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("\tadded: " + input);
            }
            System.out.println("\t————————————————————————————————————————");
            }
        }
}

