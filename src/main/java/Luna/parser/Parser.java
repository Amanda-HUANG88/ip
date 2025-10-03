package Luna.parser;

import Luna.exception.LunaException;

/**
 * Parser Class
 *
 * Deals with making sense of the user command.
 * It separates the command word from the arguments.
 */

public class Parser {

    /**
     * Parses the full user command into a command type and its arguments.
     *
     * @param fullCommand The raw string command entered by the user.
     * @return A String array where the first element is the command type and the others are the arguments.
     * @throws LunaException If the command is empty.
     */
    public static String[] parse(String fullCommand) throws LunaException {
        String trimmedCommand = fullCommand.trim();
        if (trimmedCommand.isEmpty()) {
            throw new LunaException("Command cannot be empty.");
        }

        // Split the command into command word and the rest of the arguments
        String[] parts = trimmedCommand.split(" ", 2);
        String command = parts[0].toLowerCase();

        if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new LunaException("Please provide a task number to " + command + ".");
            }
        }

        return parts;
    }
}