package zeus.parser;

import zeus.tasklist.TaskList;

import static java.lang.Integer.parseInt;

public class Parser {

	public static String getCommand(String word) {
		return word.trim().split(" ")[0];
	}

	public static int getTaskIndex(String word) {
		return parseInt(word.split(" ")[1]) - 1;
	}

	public static int getNumUserInput(String userInput) {
		return userInput.split(" ").length;
	}

	public static String getTodoDescription(String userInput) {
		return userInput.split(" ", 2)[1];
	}

	public static String[] getDeadlineParts(String userInput) {
		return userInput.split(" ", 2)[1].split(" /by ");
	}

	public static String[] getEventParts(String userInput) {
		return userInput.split(" ", 2)[1].split(" /from | /to ");
	}

	public static String getKeyword(String userInput) {
		return userInput.split(" ", 2)[1];
	}
}
