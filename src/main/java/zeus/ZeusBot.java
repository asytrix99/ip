package zeus;

import zeus.exceptions.DuplicateMarkingException;
import zeus.exceptions.EmptyListException;
import zeus.exceptions.NumArgsException;
import zeus.tasks.Task;
import zeus.tasks.Deadline;
import zeus.tasks.Todo;
import zeus.tasks.Event;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class ZeusBot {

	public static final String INDENT = "\t";
	public static final String TAB = INDENT + "____________________________________________________________";
	public static final String MSG_EMPTY_INPUT = INDENT + "Oops! You've gotta input something~";
	public static final String MSG_BYE = INDENT + "Awh... so fast? Alright then, hope to see you again soon!";
	public static final String MSG_DUPLICATE_MARK = INDENT + "You've already finished this silly!";
	public static final String MSG_MARK = INDENT + "Ah! That's amazing, you've got another one crossed out~";
	public static final String MSG_DUPLICATE_UNMARK = "I see... Trying to run away from responsibilities? It's already unmarked...";
	public static final String MSG_UNMARK = "Awh, it's alright, you can work on this next time. Keep up!";
	public static final String EMPTY_LIST_PROMPT = "You're free for the day!";
	public static final String OUT_OF_BOUNDS_PROMPT = "Your query is too large for your current list :/";
	public static final String MISSING_INDEX_PROMPT = "What are you even referring to? Add an index!";
	public static final String EXCESSIVE_INPUT_ARGS_PROMPT = "One task at a time my friend! Input only one digit~";

	public static void main(String[] args) {
		ZeusBot.greetUser();
		handleUserInput();
	}

	private static void handleUserInput() {
		Scanner sc = new Scanner(System.in);
		ArrayList<Task> todo_list = new ArrayList<>();

		while (true) {
			String echo_word = sc.nextLine();
			switch (echo_word.split(" ")[0]) {
			case "bye":
				ZeusBot.sayByeToUser();
				return;
			// Checks for empty input
			case "":
				System.out.println(MSG_EMPTY_INPUT);
				tab();
				continue;
			case "mark":
			case "unmark":
				ZeusBot.handleMarking(echo_word, todo_list);
				break;
			case "list":
				ZeusBot.listTasks(todo_list);
				break;
			default:
				ZeusBot.addTask(echo_word, todo_list);
			}
		}
	}

	private static void tab() {
		System.out.println(TAB);
	}

	public static void listTasks(ArrayList<Task> todo_list) {
		try {
			tab();
			checkEmptyList(todo_list);
			printList(todo_list);
			tab();
		} catch (EmptyListException e) {
			System.out.println(MSG_EMPTY_INPUT);
		}
	}

	public static void handleMarking(String echo_word, ArrayList<Task> todo_list) {
		try {
			// Ensures number of args == 2
			checkCorrectNumArgs(echo_word);

			// Catches if user tries to mark/unmark an empty list
			checkEmptyList(todo_list);

			int task_index = getTaskIndex(echo_word);

			//  Catches if user inputs index out of bounds
			checkOutOfBounds(todo_list, task_index);

			checkDuplicate(echo_word, todo_list, task_index);

			printTaskBar(todo_list, task_index);
			tab();
		} catch (NumArgsException | DuplicateMarkingException | EmptyListException | IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			tab();
		}
	}

	private static void checkOutOfBounds(ArrayList<Task> todoList, int task_index) {
		if (todoList.size() < task_index + 1) {
			throw new IndexOutOfBoundsException(INDENT + OUT_OF_BOUNDS_PROMPT);
		}
	}

	protected static void greetUser() {
		// Greeting user
		tab();
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
		System.out.println(INDENT + "Hey there human (I hope you are...)! I'm ZEUSBot - your unconventionally helpful sidekick!" + logo);
		System.out.println(INDENT + "What idea, plan or meme do you have on mind today?");
		tab();
		tab();
	}

	private static void checkDuplicate(String echo_word, ArrayList<Task> todo_list, int task_index) throws DuplicateMarkingException {
		// Checking for duplicate marking or unmarking
		if (echo_word.startsWith("mark")) {
			checkDuplicateMark(todo_list, task_index);
		} else {
			checkDuplicateUnmark(todo_list, task_index);
		}
	}

	private static int getTaskIndex(String echo_word) {
		return parseInt(echo_word.split(" ")[1]) - 1;
	}

	public static void addTask(String echo_word, ArrayList<Task> todo_list) {
		tab();
		String[] parts = echo_word.split(" ", 2);
		String action = parts[0];

		Task t;

		// Set task t subclass based on action
		switch (action) {
		case "todo":
			t = new Todo(parts[1]);
			break;
		case "deadline":
			String[] deadlineParts = parts[1].split(" /by ");
			t = new Deadline(deadlineParts[0], deadlineParts[1]);
			break;
		case "event":
			String[] eventParts = parts[1].split("/from | /to");
			t = new Event(eventParts[0], eventParts[1], eventParts[2]);
			break;
		default:
			System.out.println(INDENT + "Invalid action!");
			tab();
			return;
		}

		todo_list.add(t);
		System.out.println(INDENT + "Got it. I've added this task:");
		System.out.println(INDENT + " " + t);
		System.out.println(INDENT + "Now you have " + todo_list.size() + " tasks in this list.");
		tab();
	}

	public static void sayByeToUser() {
		tab();
		System.out.println(MSG_BYE);
		tab();
	}

	private static void printList(ArrayList<Task> todo_list) {
		int counter = 1;
		System.out.println(INDENT + "Here are the tasks in your list:");
		for (Task task : todo_list) {
			System.out.println(INDENT + counter + "." + task.toString());
			counter++;
		}
	}

	private static void checkEmptyList(ArrayList<Task> todo_list) throws EmptyListException {
		if (todo_list.isEmpty()) {
			tab();
			throw new EmptyListException(INDENT + EMPTY_LIST_PROMPT);
		}
	}

	private static void printTaskBar(ArrayList<Task> todo_list, int task_index) {
		String string = todo_list.get(task_index).toString();
		System.out.println(string);
	}

	private static void checkDuplicateUnmark(ArrayList<Task> todo_list, int task_index) throws DuplicateMarkingException {
		if (!todo_list.get(task_index).isDone) {
			throw new DuplicateMarkingException(INDENT + MSG_DUPLICATE_UNMARK);
		} else {
			System.out.println(INDENT + MSG_UNMARK);
			todo_list.get(task_index).isDone = false;
			tab();
		}
	}

	private static void checkDuplicateMark(ArrayList<Task> todo_list, int task_index) throws DuplicateMarkingException {
		if (todo_list.get(task_index).isDone) {
			throw new DuplicateMarkingException(INDENT + MSG_DUPLICATE_MARK);
		} else {
			System.out.println(INDENT + MSG_MARK);
			todo_list.get(task_index).isDone = true;
			tab();
		}
	}

	private static void checkCorrectNumArgs(String echo_word) throws NumArgsException {
		if (echo_word.split(" ").length == 1) {
			tab();
			throw new NumArgsException(INDENT + MISSING_INDEX_PROMPT);
		} else if (echo_word.split(" ").length > 2) {
			tab();
			throw new NumArgsException(INDENT + EXCESSIVE_INPUT_ARGS_PROMPT);
		}
	}
}