package zeus.parser;

import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	 * @throws IllegalArgumentException If input does not match the required number of arguments or specified format.
	 */
	public static String[] getDeadlineParts(String userInput) {
		String[] parts = userInput.split(" ", 2)[1].split(" /by ");
		if (parts.length < 2) {
			throw new IllegalArgumentException("\tFollow deadline format as follows: deadline <description> /by yyyy-MM-dd");
		}
		return parts;
	}

	/**
	 * Parses a date string from {@code yyyy-MM-dd} format into a {@code LocalDate}.
	 *
	 * @param date A date string that follows the {@code yyyy-MM-dd} format.
	 * @return The parsed {@code LocalDate}.
	 */
	public static LocalDate parseDate(String date) {
		return LocalDate.parse(date);
	}

	/**
	 * Formats a {@code localDate} into a visualised text string in the required {@code MMM d yyy} format.
	 *
	 * @param date The {@code localDate} to format.
	 * @return A final formatted string in the form {@code MMM d yyy} (e.g., "Dec 10 2025").
	 */
	public static String formatDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMM d yyy"));
	}

	/**
	 * Extracts the description given when a {@code event} command is called.
	 *
	 * @param userInput The full user input string.
	 * @return The description of the event task.
	 * @throws IllegalArgumentException If input does not match the required number of arguments or specified format.
	 */
	public static String[] getEventParts(String userInput) {
		String withoutCommand = userInput.split(" ", 2)[1];
		String[] parts = withoutCommand.split(" /from | /to ");
		if (parts.length < 3) {
			throw new IllegalArgumentException("\tFollow event format as follows: event <description> /from yyyy-MM-dd /to yyyy-MM-dd");
		}
		return parts;
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
