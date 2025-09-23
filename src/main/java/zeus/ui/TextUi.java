package zeus.ui;

import zeus.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls all user interaction for ZeusBot
 * <p>
 * Contains methods for displaying messages, prompts, todo list,
 * and error messages.
 */
public class TextUi {
	public final String INDENT = "\t";
	public final String LINE = INDENT + "____________________________________________________________";
	public final String MSG_BYE = INDENT + "Awh... so fast? Alright then, hope to see you again soon!";
	public final String MSG_EMPTY_INPUT = INDENT + "Oops! You've gotta input something~";
	public final String MSG_DUPLICATE_MARK = INDENT + "You've already finished this silly!";
	public final String MSG_MARK = INDENT + "Ah! That's amazing, you've got another one crossed out~";
	public final String MSG_DUPLICATE_UNMARK = "I see... Trying to run away from responsibilities? It's already unmarked...";
	public final String MSG_UNMARK = "Awh, it's alright, you can work on this next time. Keep up!";
	public final String EMPTY_LIST_PROMPT = "You're free for the day!";
	public final String OUT_OF_BOUNDS_TOO_BIG_PROMPT = "Your query is too LARGE for your current list :/";
	public final String OUT_OF_BOUNDS_TOO_SMALL_PROMPT = "Your query is too SMALL for your current list :/ ";
	public final String MISSING_INDEX_PROMPT = "What are you even referring to? Add an index!";
	public final String EXCESSIVE_INPUT_ARGS_PROMPT = "One task at a time my friend! Input only one digit~";
	public final String NO_SUCH_KEYWORD_PROMPT = "That's just a magic word! No such task in your list dear...";

	/**
	 * Prints out a horizontal diver line.
	 */
	public void showLine() {
		System.out.println(LINE);
	}

	/**
	 * Returns fixed indentation string used when messages are printed.
	 *
	 * @return The indentation string.
	 */
	public String showIndent() {
		return INDENT;
	}

	/**
	 * Displays greeting message and ZeusBot logo using ASCII art at program start.
	 */
	public void showGreeting() {
		showLine();
		// Logo generated from https://patorjk.com/software/taag/
		String logo = """
				 _____                                                                                                      _____\s
				( ___ )----------------------------------------------------------------------------------------------------( ___ )
				 |   |                                                                                                      |   |\s
				 |   |           _____                    _____                    _____                    _____           |   |\s
				 |   |          /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\          |   |\s
				 |   |         /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\         |   |\s
				 |   |         \\:::\\    \\              /::::\\    \\              /:::/    /               /::::\\    \\        |   |\s
				 |   |          \\:::\\    \\            /::::::\\    \\            /:::/    /               /::::::\\    \\       |   |\s
				 |   |           \\:::\\    \\          /:::/\\:::\\    \\          /:::/    /               /:::/\\:::\\    \\      |   |\s
				 |   |            \\:::\\    \\        /:::/__\\:::\\    \\        /:::/    /               /:::/__\\:::\\    \\     |   |\s
				 |   |             \\:::\\    \\      /::::\\   \\:::\\    \\      /:::/    /                \\:::\\   \\:::\\    \\    |   |\s
				 |   |              \\:::\\    \\    /::::::\\   \\:::\\    \\    /:::/    /      _____    ___\\:::\\   \\:::\\    \\   |   |\s
				 |   |               \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/____/      /\\    \\  /\\   \\:::\\   \\:::\\    \\  |   |\s
				 |   | _______________\\:::\\____\\/:::/__\\:::\\   \\:::\\____\\|:::|    /      /::\\____\\/::\\   \\:::\\   \\:::\\____\\ |   |\s
				 |   | \\::::::::::::::::::/    /\\:::\\   \\:::\\   \\::/    /|:::|____\\     /:::/    /\\:::\\   \\:::\\   \\::/    / |   |\s
				 |   |  \\::::::::::::::::/____/  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\   /:::/    /  \\:::\\   \\:::\\   \\/____/  |   |\s
				 |   |   \\:::\\~~~~\\~~~~~~         \\:::\\   \\:::\\    \\       \\:::\\    \\ /:::/    /    \\:::\\   \\:::\\    \\      |   |\s
				 |   |    \\:::\\    \\               \\:::\\   \\:::\\____\\       \\:::\\    /:::/    /      \\:::\\   \\:::\\____\\     |   |\s
				 |   |     \\:::\\    \\               \\:::\\   \\::/    /        \\:::\\__/:::/    /        \\:::\\  /:::/    /     |   |\s
				 |   |      \\:::\\    \\               \\:::\\   \\/____/          \\::::::::/    /          \\:::\\/:::/    /      |   |\s
				 |   |       \\:::\\    \\               \\:::\\    \\               \\::::::/    /            \\::::::/    /       |   |\s
				 |   |        \\:::\\____\\               \\:::\\____\\               \\::::/    /              \\::::/    /        |   |\s
				 |   |         \\::/    /                \\::/    /                \\::/____/                \\::/    /         |   |\s
				 |   |          \\/____/                  \\/____/                  ~~                       \\/____/          |   |\s
				 |___|                                                                                                      |___|\s
				(_____)----------------------------------------------------------------------------------------------------(_____)""";
		System.out.println(showIndent() + "Hey there human (I hope you are...)! I'm ZEUSBot - your unconventionally helpful sidekick!" + "\n" + logo);
		System.out.println(showIndent() + "What idea, plan or meme do you have on mind today?");
		showLine();
		showLine();
	}

	/**
	 * Displays goodbye message when program exits.
	 */
	public void showBye() {
		showLine();
		System.out.println(MSG_BYE);
		showLine();
	}

	/**
	 * Displays message when adding a new task.
	 *
	 * @param task The task that was added.
	 * @param size The updated total number of tasks in the list.
	 */
	public void showTaskAdded(Task task, int size) {
		System.out.println(INDENT + "Got it. I've added this task:");
		System.out.println(INDENT + " " + task);
		System.out.println(INDENT + "Now you have " + size + " tasks in this list.");
		showLine();
	}

	/**
	 * Takes in the next line of input from standard input.
	 *
	 * @return The full line of input by user.
	 */
	public String readCommand() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	/**
	 * Displays a message when no input entered.
	 */
	public void showEmptyInput() {
		System.out.println(MSG_EMPTY_INPUT);
		showLine();
	}

	/** @return Error message for missing index in the input */
	public String missingIndexError() {
		return showIndent() + MISSING_INDEX_PROMPT;
	}

	/** @return Error message for too many arguments provided. */
	public String excessiveInputError() {
		return showIndent() + EXCESSIVE_INPUT_ARGS_PROMPT;
	}

	/** @return Error message for empty todo list */
	public String emptyInputError() {
		return showIndent() + EMPTY_LIST_PROMPT;
	}

	/** @return Error message when input index less than allowed. */
	public String outOfBoundsInputErrorTooSmall() {
		return showIndent() + OUT_OF_BOUNDS_TOO_SMALL_PROMPT;
	}
	/** @return Error message when input index more than allowed. */
	public String outOfBoundsInputErrorTooBig() {
		return showIndent() + OUT_OF_BOUNDS_TOO_BIG_PROMPT;
	}

	/** @return Message shown when marking an already marked task is attempted. */
	public String indicateDuplicatedMarkedTask() {
		return MSG_DUPLICATE_MARK;
	}

	/** @return Message shown when task marked successfully. */
	public String indicateMarkedTask() {
		return MSG_MARK;
	}

	/** @return Message shown when unmarking an already unmarked task is attempted. */
	public String indicateDuplicatedUnmarkedTask() {
		return showIndent() + MSG_DUPLICATE_UNMARK;
	}

	/** @return Message shown when task unmarked successfully. */
	public String indicateUnmarkedTask() {
		return showIndent() + MSG_UNMARK;
	}

	/**
	 * Prints out a single task from todo list according to index given.\
	 *
	 * @param todo_list The list of tasks.
	 * @param task_index The given index of task to print.
	 */
	public void printTaskBar(ArrayList<Task> todo_list, int task_index) {
		String string = todo_list.get(task_index).toString();
		System.out.println(string);
	}

	/**
	 * Displays a message when a task is removed from todo list.
	 *
	 * @param todo_list The list of the tasks.
	 * @param task_index The given index of task to print.
	 */
	public void showTaskDeleted(ArrayList<Task> todo_list, int task_index) {
		showLine();
		System.out.println(showIndent() + "Awh, sad to see your task go away :(");
		System.out.println(showIndent() + " " + todo_list.get(task_index));
		System.out.println(showIndent() + "One less task... You have " + todo_list.size() + " tasks left!");
		showLine();
	}

	/**
	 * Prints out all tasks currently in the todo list.
	 *
	 * @param todo_list The list of the tasks.
	 */
	public void printList(ArrayList<Task> todo_list) {
		int counter = 1;
		showLine();
		System.out.println(showIndent() + "Here are the tasks in your list:");
		for (Task task : todo_list) {
			System.out.println(showIndent() + counter + "." + task.toString());
			counter++;
		}
	}

	/**
	 * Displays an error message from exception.
	 *
	 * @param message The error message to display.
	 */
	public void showExceptionError(String message) {
		showLine();
		System.out.println(message);
		showLine();
	}

	/**
	 * Displays a message when entering an invalid command.
	 */
	public void showInvalidCommand() {
		System.out.println(showIndent() + "Invalid action!");
		showLine();
	}

	/**
	 * Displays a message when entered keyword does not exist.
	 */
	public void showNoSuchKeyword() {
		showLine();
		System.out.println(showIndent() + NO_SUCH_KEYWORD_PROMPT);
		showLine();
	}
}
