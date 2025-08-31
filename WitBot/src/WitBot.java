import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class WitBot {

	public static final String TAB = "\t____________________________________________________________";
	public static final String MSG_EMPTY_INPUT = "Oops! You've gotta input something~";
	public static final String MSG_BYE = "\tAwh... so fast? Alright then, hope to see you again soon!";
	public static final String MSG_DUPLICATE_MARK = "You've already finished this silly!";
	public static final String MSG_DUPLICATE_UNMARK = "Ah! That's amazing, you've got another one crossed out~";

	public static void main(String[] args) {
		greetUser();

        Scanner sc = new Scanner(System.in);
        String echo_word = "";
        ArrayList<Task> todo_list = new ArrayList<>(); //array with class type
        int task_index = 0;

        while (true) {
            echo_word = sc.nextLine();
            if (echo_word.equals("bye")) {
                break;
            } else if (echo_word.isEmpty()) {
				warnEmptyInput(); //check empty input
			} else if (echo_word.startsWith("mark") || echo_word.startsWith("unmark")){
				handleDuplicateMarking(echo_word, todo_list); //ensure num args == 2
			} else if (echo_word.equals("list")) {
				listTasks(todo_list); //list current tasks
			} else {
				addTask(echo_word, todo_list); //add task to list
            }
        }

		byeUser(); //says bye to user
	}

	private static void listTasks(ArrayList<Task> todo_list) {
		int list_counter = 1;
		System.out.println(TAB);
		checkEmptyList(todo_list);
		printList(todo_list);
		System.out.println(TAB);
	}
	private static void handleDuplicateMarking(String echo_word, ArrayList<Task> todo_list) {
		int task_index;
		if (checkCorrectNumArgs(echo_word)) return;
		System.out.println(TAB);
		task_index = getTaskIndex(echo_word);

		if (checkDuplicate(echo_word, todo_list, task_index)) return;
		printTaskBar(todo_list, task_index);
		System.out.println(TAB);
	}
	private static void warnEmptyInput() {
		System.out.println(MSG_EMPTY_INPUT);
		System.out.println(TAB);
		return;
	}
	private static String greetUser() {
		//        greeting user
		System.out.println(TAB);
		String logo = "       __          ___   _      \n" +
				"       \\ \\        / / | (_) |_   \n" +
				"        \\ \\  /\\  / /  | | | __|  \n" +
				"         \\ \\/  \\/ /   | | | |_   \n" +
				"          \\__/\\__/    |_|  \\__|  \n";
		System.out.println("\tHey there human (I hope you are...)! I'm WitBot - your unconventionally helpful sidekick!" + logo);
		System.out.println("\tWhat idea, plan or meme do you have on mind today?");
		System.out.println(TAB);
		return TAB;
	}
	private static boolean checkDuplicate(String echo_word, ArrayList<Task> todo_list, int task_index) {
		if (echo_word.startsWith("mark")) {
//                    duplicate mark check
			if (checkDuplicateMark(todo_list, task_index)) return true;
		} else {
//                    duplicate unmark check
			if (checkDuplicateUnmark(todo_list, task_index)) return true;
		}
		return false;
	}
	private static int getTaskIndex(String echo_word) {
		int task_index;
		task_index = parseInt(echo_word.split(" ")[1]) - 1;
		return task_index;
	}
	private static void addTask(String echo_word, ArrayList<Task> todo_list) {
		System.out.println(TAB);
		Task t = new Task(echo_word);
		todo_list.add(t);
		t.description = echo_word;
		System.out.println("\tadded: " + echo_word);
		System.out.println(TAB);
	}
	private static void byeUser() {
		System.out.println(TAB);
		System.out.println(MSG_BYE);
		System.out.println(TAB);
	}
	private static void printList(ArrayList<Task> todo_list) {
		int counter = 1;
		for (Task task : todo_list) {
			System.out.println("\t" + counter + ". [" + task.getStatusIcon() + "] " + task.description);
			counter++;
		}
	}
	private static void checkEmptyList(ArrayList<Task> todo_list) {
		if (todo_list.isEmpty()) {
			System.out.println("Good on ya, you're free for the day!");
		}
	}
	private static void printTaskBar(ArrayList<Task> todo_list, int task_index) {
		todo_list.get(task_index).print_taskbar(todo_list.get(task_index).description);
	}
	private static boolean checkDuplicateUnmark(ArrayList<Task> todo_list, int task_index) {
		if (!todo_list.get(task_index).isDone) {
			System.out.println("I see... Trying to run away from responsibilities? It's already unmarked...");
			System.out.println(TAB);
			return true;
		} else {
			System.out.println("Awh, it's alright, you can work on this next time. Keep up!");
			todo_list.get(task_index).isDone = false;
		}
		return false;
	}
	private static boolean checkDuplicateMark(ArrayList<Task> todo_list, int task_index) {
		if (todo_list.get(task_index).isDone) {
			System.out.println(MSG_DUPLICATE_MARK);
			System.out.println(TAB);
			return true;
		} else {
			System.out.println(MSG_DUPLICATE_UNMARK);
			todo_list.get(task_index).isDone = true;
			System.out.println(TAB);
		}
		return false;
	}
	private static boolean checkCorrectNumArgs(String echo_word) {
		if (echo_word.split(" ").length == 1) {
			System.out.println(TAB);
			System.out.println("What are you even referring to? Add an index!");
			System.out.println(TAB);
			return true;
		} else if (echo_word.split(" ").length > 2) {
			System.out.println(TAB);
			System.out.println("One task at a time my friend! Input only one digit~");
			System.out.println(TAB);
			return true;
		}
		return false;
	}
}