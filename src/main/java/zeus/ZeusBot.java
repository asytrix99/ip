	package zeus;

	import zeus.ui.TextUi;
	import zeus.tasklist.TaskList;
	import zeus.storage.StorageFile;
	import zeus.parser.Parser;
	import zeus.exceptions.DuplicateMarkingException;
	import zeus.exceptions.EmptyListException;
	import zeus.exceptions.NumArgsException;
	import zeus.tasks.Task;

	import java.util.ArrayList;

	public class ZeusBot {


		private static StorageFile storage;
		private static TaskList todo_list;
		private static TextUi ui;

		public ZeusBot(String filePath) {
			ui = new TextUi();
			storage = new StorageFile(filePath);
			try {
				todo_list = new TaskList(storage.loadTasks());
			} catch (Exception e) {
				ui.showExceptionError("Could not load tasks, empty list initiated.");
				todo_list = new TaskList();
			}
		}

		public void run() {
			ui.showGreeting();
			while (true) {
				try {
					String input = ui.readCommand();
					String command = Parser.getCommand(input);

					switch (command) {
					case "":
						ui.showEmptyInput();
						break;
					case "bye":
						ui.showBye();
						return;
					case "mark":
					case "unmark":
						handleMarking(input);
						break;
					case "delete":
						deleteTask(input);
						break;
					case "list":
						listTasks();
						break;
					case "find":
						findTask(input);
						break;
					default:
						addTask(input, command);
						storage.saveTasks(todo_list.returnAllTasks());
					}
				} catch (Exception e) {
					ui.showExceptionError(e.getMessage());
				}
			}
		}

		public static void main(String[] args) {
			new ZeusBot("./data/zeusbot.txt").run();
		}

		public static void deleteTask(String userInput) {
			try {
				checkCorrectNumArgs(userInput);
				checkEmptyList();
				int task_index = Parser.getTaskIndex(userInput);
				checkOutOfBounds(task_index);

				ui.showTaskDeleted(todo_list.returnAllTasks(), task_index);
				todo_list.deleteTask(task_index);
				storage.saveTasks(todo_list.returnAllTasks());
			} catch (EmptyListException | NumArgsException | IndexOutOfBoundsException e) {
				ui.showExceptionError(e.getMessage());
			}
		}

		public static void listTasks() {
			try {
				checkEmptyList();
				ui.printList(todo_list.returnAllTasks());
				ui.showLine();
			} catch (EmptyListException e) {
				ui.showExceptionError(e.getMessage());
			}
		}

		public static void findTask(String userInput) {
			try {
				checkCorrectNumArgs(userInput);
				checkEmptyList();
				String keyWord = Parser.getKeyword(userInput);
				ArrayList<Task> filteredList = todo_list.getFilteredList(keyWord, todo_list);
				if (filteredList.isEmpty()) {
					ui.showNoSuchKeyword();
					return;
				}
				ui.printList(filteredList);
				ui.showLine();
			} catch (EmptyListException | NumArgsException e) {
				ui.showExceptionError(e.getMessage());
			}
		}

		public static void handleMarking(String echo_word) {
			try {
				// Ensures number of args == 2
				checkCorrectNumArgs(echo_word);
				// Catches if user tries to mark/unmark an empty list
				checkEmptyList();
				int task_index = Parser.getTaskIndex(echo_word);
				//  Catches if user inputs index out of bounds
				checkOutOfBounds(task_index);
				checkDuplicate(echo_word, task_index);
				ui.printTaskBar(todo_list.returnAllTasks(), task_index);
				ui.showLine();
			} catch (NumArgsException | DuplicateMarkingException | EmptyListException | IndexOutOfBoundsException e) {
				ui.showExceptionError(e.getMessage());
			}
		}

		private static void checkOutOfBounds(int task_index) {
			if (task_index < 0) {
				throw new IndexOutOfBoundsException(ui.outOfBoundsInputErrorTooSmall());
			}
			if (task_index >= todo_list.size()) {
				throw new IndexOutOfBoundsException(ui.outOfBoundsInputErrorTooBig());
			}
		}

		private static void checkDuplicate(String echo_word, int task_index) throws DuplicateMarkingException {
			// Checking for duplicate marking or unmarking
			if (echo_word.startsWith("mark")) {
				checkDuplicateMark(task_index);
			} else {
				checkDuplicateUnmark(task_index);
			}
		}

		public static void addTask(String echo_word, String command) {
			ui.showLine();
			Task t;
			// Set task t subclass based on action
			switch (command) {
			case "todo":
				t = todo_list.addTodo(Parser.getTodoDescription(echo_word));
				break;
			case "deadline":
				String[] deadlineParts = Parser.getDeadlineParts(echo_word);
				t = todo_list.addDeadline(deadlineParts[0], deadlineParts[1]);
				break;
			case "event":
				String [] eventParts = Parser.getEventParts(echo_word);
				t = todo_list.addEvent(eventParts[0], eventParts[1], eventParts[2]);
				break;
			default:
				ui.showInvalidCommand();
				return;
			}

			ui.showTaskAdded(t, todo_list.size());
		}

		private static void checkEmptyList() throws EmptyListException {
			if (todo_list.isEmpty()) {
				throw new EmptyListException(ui.emptyInputError());
			}
		}

		private static void checkDuplicateUnmark(int task_index) throws DuplicateMarkingException {
			if (!todo_list.get(task_index).isDone) {
				throw new DuplicateMarkingException(ui.indicateDuplicatedUnmarkedTask());
			} else {
				ui.showLine();
				System.out.println(ui.indicateUnmarkedTask());
				todo_list.get(task_index).isDone = false;
				// Persist saving input after every task unmarking
				storage.saveTasks(todo_list.returnAllTasks());
			}
		}

		private static void checkDuplicateMark(int task_index) throws DuplicateMarkingException {
			if (todo_list.get(task_index).isDone) {
				throw new DuplicateMarkingException(ui.indicateDuplicatedMarkedTask());
			} else {
				ui.showLine();
				System.out.println(ui.indicateMarkedTask());
				todo_list.get(task_index).isDone = true;
				// Persist saving input after every task marking
				storage.saveTasks(todo_list.returnAllTasks());
			}
		}

		private static void checkCorrectNumArgs(String echo_word) throws NumArgsException {
			if (Parser.getNumUserInput(echo_word) == 1) {
				throw new NumArgsException(ui.missingIndexError());
			} else if (Parser.getNumUserInput(echo_word) > 2) {
				throw new NumArgsException(ui.excessiveInputError());
			}
		}
	}