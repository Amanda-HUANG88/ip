import java.util.Scanner;

public class Luna {
    public static void main(String[] args) {
        System.out.println("————————————————————————————————————————");
        System.out.println("Hello! I'm Luna");
        System.out.println("What can I do for you?");
        System.out.println("————————————————————————————————————————");

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            System.out.println("————————————————————————————————————————");
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("————————————————————————————————————————");
                break;
            } else {
                System.out.println(input);
                System.out.println("————————————————————————————————————————");
            }
        }
    }
}

