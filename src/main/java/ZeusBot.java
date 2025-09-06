import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class ZeusBot {

	public static final String INDENT = "\t";
	public static final String TAB = INDENT + "____________________________________________________________";
	public static final String MSG_EMPTY_INPUT = INDENT + "Oops! You've gotta input something~";
	public static final String MSG_BYE = INDENT + "Awh... so fast? Alright then, hope to see you again soon!";
	public static final String MSG_DUPLICATE_MARK = INDENT + "You've already finished this silly!";
	public static final String MSG_DUPLICATE_UNMARK = INDENT + "Ah! That's amazing, you've got another one crossed out~";

	public static void main(String[] args) {
		ZeusBot.greetUser();
		handleUserInput();
	}

	private static void handleUserInput() {
		Scanner sc = new Scanner(System.in);
		ArrayList<Task> todo_list = new ArrayList<>();

		while (true) {
			String echo_word = sc.nextLine();
			switch (echo_word) {
			case "bye":
				ZeusBot.byeUser();
				return;
			// Checks for empty input
			case "":
				ZeusBot.warnEmptyInput();
				continue;
			case "mark":
			case "unmark":
				ZeusBot.handleMarking(echo_word, todo_list);
			case "list":
				ZeusBot.listTasks(todo_list);
			default:
				ZeusBot.addTask(echo_word, todo_list);
			}
		}
	}

	private static void tab() {
		System.out.println(TAB);
	}

	public static void listTasks(ArrayList<Task> todo_list) {
		tab();
		checkEmptyList(todo_list);
		printList(todo_list);
		tab();
	}

	public static void handleMarking(String echo_word, ArrayList<Task> todo_list) {
		int task_index;
		// Ensures number of args == 2
		if (checkCorrectNumArgs(echo_word)) {
			return;
		}
		task_index = getTaskIndex(echo_word);

		if (checkDuplicate(echo_word, todo_list, task_index)) {
			return;
		}
		printTaskBar(todo_list, task_index);
		tab();
	}

	public static void warnEmptyInput() {
		System.out.println(MSG_EMPTY_INPUT);
		tab();
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

	private static boolean checkDuplicate(String echo_word, ArrayList<Task> todo_list, int task_index) {
		// Checking for duplicate marking/unmarking
		if (echo_word.startsWith("mark")) {
			return checkDuplicateMark(todo_list, task_index);
		} else {
			return checkDuplicateUnmark(todo_list, task_index);
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

	public static void byeUser() {
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

	private static void checkEmptyList(ArrayList<Task> todo_list) {
		if (todo_list.isEmpty()) {
			System.out.println("Good on ya, you're free for the day!");
		}
	}

	private static void printTaskBar(ArrayList<Task> todo_list, int task_index) {
		String string = todo_list.get(task_index).toString();
		System.out.println(string);
	}

	private static boolean checkDuplicateUnmark(ArrayList<Task> todo_list, int task_index) {
		if (!todo_list.get(task_index).isDone) {
			System.out.println(INDENT + "I see... Trying to run away from responsibilities? It's already unmarked...");
			tab();
			return true;
		} else {
			System.out.println(INDENT + "Awh, it's alright, you can work on this next time. Keep up!");
			todo_list.get(task_index).isDone = false;
			tab();
		}
		return false;
	}

	private static boolean checkDuplicateMark(ArrayList<Task> todo_list, int task_index) {
		if (todo_list.get(task_index).isDone) {
			System.out.println(MSG_DUPLICATE_MARK);
			tab();
			return true;
		} else {
			System.out.println(MSG_DUPLICATE_UNMARK);
			todo_list.get(task_index).isDone = true;
			tab();
		}
		return false;
	}

	private static boolean checkCorrectNumArgs(String echo_word) {
		if (echo_word.split(" ").length == 1) {
			tab();
			System.out.println("What are you even referring to? Add an index!");
			tab();
			return true;
		} else if (echo_word.split(" ").length > 2) {
			tab();
			System.out.println("One task at a time my friend! Input only one digit~");
			tab();
			return true;
		}
		return false;
	}
}