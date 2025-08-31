package main.java;

import java.util.ArrayList;
import static java.lang.Integer.parseInt;

public class ZeusBot {

	public static final String TAB = "\t____________________________________________________________";
	public static final String MSG_EMPTY_INPUT = "\tOops! You've gotta input something~";
	public static final String MSG_BYE = "\tAwh... so fast? Alright then, hope to see you again soon!";
	public static final String MSG_DUPLICATE_MARK = "\tYou've already finished this silly!";
	public static final String MSG_DUPLICATE_UNMARK = "\tAh! That's amazing, you've got another one crossed out~";

	private static void tab() {
		System.out.println("\t____________________________________________________________");
	}
	public static void listTasks(ArrayList<Task> todo_list) {
		int list_counter = 1;
		tab();
		checkEmptyList(todo_list);
		printList(todo_list);
		tab();
	}
	public static void handleMarking(String echo_word, ArrayList<Task> todo_list) {
		int task_index;
		if (checkCorrectNumArgs(echo_word)) return;
		tab();
		task_index = getTaskIndex(echo_word);

		if (checkDuplicate(echo_word, todo_list, task_index)) return;
		printTaskBar(todo_list, task_index);
	}
	public static void warnEmptyInput() {
		System.out.println(MSG_EMPTY_INPUT);
		tab();
	}
	protected static void greetUser() {
		//        greeting user
		tab();
		String logo = " _____                                                                                                      _____ \n" +
				"( ___ )----------------------------------------------------------------------------------------------------( ___ )\n" +
				" |   |                                                                                                      |   | \n" +
				" |   |           _____                    _____                    _____                    _____           |   | \n" +
				" |   |          /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\          |   | \n" +
				" |   |         /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\         |   | \n" +
				" |   |         \\:::\\    \\              /::::\\    \\              /:::/    /               /::::\\    \\        |   | \n" +
				" |   |          \\:::\\    \\            /::::::\\    \\            /:::/    /               /::::::\\    \\       |   | \n" +
				" |   |           \\:::\\    \\          /:::/\\:::\\    \\          /:::/    /               /:::/\\:::\\    \\      |   | \n" +
				" |   |            \\:::\\    \\        /:::/__\\:::\\    \\        /:::/    /               /:::/__\\:::\\    \\     |   | \n" +
				" |   |             \\:::\\    \\      /::::\\   \\:::\\    \\      /:::/    /                \\:::\\   \\:::\\    \\    |   | \n" +
				" |   |              \\:::\\    \\    /::::::\\   \\:::\\    \\    /:::/    /      _____    ___\\:::\\   \\:::\\    \\   |   | \n" +
				" |   |               \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/____/      /\\    \\  /\\   \\:::\\   \\:::\\    \\  |   | \n" +
				" |   | _______________\\:::\\____\\/:::/__\\:::\\   \\:::\\____\\|:::|    /      /::\\____\\/::\\   \\:::\\   \\:::\\____\\ |   | \n" +
				" |   | \\::::::::::::::::::/    /\\:::\\   \\:::\\   \\::/    /|:::|____\\     /:::/    /\\:::\\   \\:::\\   \\::/    / |   | \n" +
				" |   |  \\::::::::::::::::/____/  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\   /:::/    /  \\:::\\   \\:::\\   \\/____/  |   | \n" +
				" |   |   \\:::\\~~~~\\~~~~~~         \\:::\\   \\:::\\    \\       \\:::\\    \\ /:::/    /    \\:::\\   \\:::\\    \\      |   | \n" +
				" |   |    \\:::\\    \\               \\:::\\   \\:::\\____\\       \\:::\\    /:::/    /      \\:::\\   \\:::\\____\\     |   | \n" +
				" |   |     \\:::\\    \\               \\:::\\   \\::/    /        \\:::\\__/:::/    /        \\:::\\  /:::/    /     |   | \n" +
				" |   |      \\:::\\    \\               \\:::\\   \\/____/          \\::::::::/    /          \\:::\\/:::/    /      |   | \n" +
				" |   |       \\:::\\    \\               \\:::\\    \\               \\::::::/    /            \\::::::/    /       |   | \n" +
				" |   |        \\:::\\____\\               \\:::\\____\\               \\::::/    /              \\::::/    /        |   | \n" +
				" |   |         \\::/    /                \\::/    /                \\::/____/                \\::/    /         |   | \n" +
				" |   |          \\/____/                  \\/____/                  ~~                       \\/____/          |   | \n" +
				" |___|                                                                                                      |___| \n" +
				"(_____)----------------------------------------------------------------------------------------------------(_____)";
		System.out.println("\tHey there human (I hope you are...)! I'm ZEUSBot - your unconventionally helpful sidekick!" + logo);
		System.out.println("\tWhat idea, plan or meme do you have on mind today?");
		tab();
		tab();
	}
	private static boolean checkDuplicate(String echo_word, ArrayList<Task> todo_list, int task_index) {
		if (echo_word.startsWith("mark")) {
//                    duplicate mark check
			return checkDuplicateMark(todo_list, task_index);
		} else {
//                    duplicate unmark check
			return checkDuplicateUnmark(todo_list, task_index);
		}
	}
	private static int getTaskIndex(String echo_word) {
		int task_index;
		task_index = parseInt(echo_word.split(" ")[1]) - 1;
		return task_index;
	}
	public static void addTask(String echo_word, ArrayList<Task> todo_list) {
		tab();
		String[] parts = echo_word.split(" ", 2);
		String action = parts[0];

		Task t = null;

		switch (action) { //set task t subclass based on action
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
			System.out.println("\tInvalid action!");
			tab();
			return;
		}

		todo_list.add(t);
		System.out.println("\tGot it. I've added this task:");
		System.out.println("\t " + t);
		System.out.println("\tNow you have " + todo_list.size() + " tasks in this list.");
		tab();
	}
	public static void byeUser() {
		tab();
		System.out.println(MSG_BYE);
		tab();
	}
	private static void printList(ArrayList<Task> todo_list) {
		int counter = 1;
		System.out.println("\tHere are the tasks in your list:");
		for (Task task : todo_list) {
			System.out.println("\t" + counter + "." + task.toString());
			counter++;
		}
	}
	private static void checkEmptyList(ArrayList<Task> todo_list) {
		if (todo_list.isEmpty()) {
			System.out.println("Good on ya, you're free for the day!");
		}
	}
	private static void printTaskBar(ArrayList<Task> todo_list, int task_index) {
		todo_list.get(task_index).toString();
	}
	private static boolean checkDuplicateUnmark(ArrayList<Task> todo_list, int task_index) {
		if (!todo_list.get(task_index).isDone) {
			System.out.println("\tI see... Trying to run away from responsibilities? It's already unmarked...");
			tab();
			return true;
		} else {
			System.out.println("\tAwh, it's alright, you can work on this next time. Keep up!");
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