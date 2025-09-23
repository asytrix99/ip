package zeus.parser;

import static java.lang.Integer.parseInt;

/**
 * Offers utility methods for parsing user input
 * into usable components available for ZeusBot.
 */
public class Parser {

	/**
	 * Extracts command keyword from user input.

	 * @param word The full user input.
	 * @return The command keyword.
	 */
	public static String getCommand(String word) {
		return word.trim().split(" ")[0];
	}

	/**
	 * Extracts task index from user input.

	 * @param word The full user input string.
	 * @return The task index that is zero-based.
	 */
	public static int getTaskIndex(String word) {
		return parseInt(word.split(" ")[1]) - 1;
	}

	/**
	 * Returns the argument count of user input separated by whitespace.

	 * @param userInput The full user input string.
	 * @return The argument count of the input.
	 */
	public static int getNumUserInput(String userInput) {
		return userInput.split(" ").length;
	}

	/**
	 * Extracts the description given when a {@code todo} command is called.
	 *
	 * @param userInput The full user input string.
	 * @return The description of the todo task.
	 */
	public static String getTodoDescription(String userInput) {
		return userInput.split(" ", 2)[1];
	}

	/**
	 * Extracts the description given when a {@code deadline} command is called.
	 *
	 * @param userInput The full user input string.
	 * @return The description of the deadline task.
	 */
	public static String[] getDeadlineParts(String userInput) {
		return userInput.split(" ", 2)[1].split(" /by ");
	}

	/**
	 * Extracts the description given when a {@code event} command is called.
	 *
	 * @param userInput The full user input string.
	 * @return The description of the event task.
	 */
	public static String[] getEventParts(String userInput) {
		return userInput.split(" ", 2)[1].split(" /from | /to ");
	}

	/**
	 * Extracts the keyword given when a {@code find} command is called.
	 *
	 * @param userInput The full user input string.
	 * @return The keyword provided to find.
	 */
	public static String getKeyword(String userInput) {
		return userInput.split(" ", 2)[1];
	}
}
