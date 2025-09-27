	package zeus;

	import zeus.ui.TextUi;
	import zeus.tasklist.TaskList;
	import zeus.storage.StorageFile;
	import zeus.parser.Parser;
	import zeus.exceptions.DuplicateMarkingException;
	import zeus.exceptions.EmptyListException;
	import zeus.exceptions.NumArgsException;
	import zeus.tasks.Task;

	import java.time.LocalDate;
	import java.time.format.DateTimeParseException;
	import java.util.ArrayList;

	/**
	 * Main class for the ZeusBot application
	 * <p>
	 * ZeusBot is a task handling chatbot that allows users to create,
	 * list, find, mark/unmark, and delete tasks via text commands.
	 * Tasks are saved in a text file in between usage of the chatbot.
	 *
	 * @author Andrew Gan
	 * @version 1.0
	 * @since 2025-09-22
	 */
	public class ZeusBot {

		private static StorageFile storage;
		private static TaskList todo_list;
		private static TextUi ui;

		/**
		 * Initiates a new ZeusBot instance and loads in tasks from the file path provided.
		 * Empty list of tasks is initialised if tasks cannot be loaded.
		 *
		 * @param filePath The path to the text file used for saving and loading tasks.
		 */
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

		/**
		 * In charge of running the main executional loop of the chatbot.
		 * In loop, takes in user input and executes commands until "bye" is entered.
		 */
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

		/**
		 * Application program's entry point.
		 *
		 * @param args Command-line arguments (not used).
		 */
		public static void main(String[] args) {
			new ZeusBot("./data/zeusbot.txt").run();
		}

		/**
		 * Deletes a task from list of tasks according to user input.
		 *
		 * @param userInput The entire input by user, containing the delete command and index.
		 */
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

		/**
		 * Prints out all tasks currently existing in the task list.
		 */
		public static void listTasks() {
			try {
				checkEmptyList();
				ui.printList(todo_list.returnAllTasks());
				ui.showLine();
			} catch (EmptyListException e) {
				ui.showExceptionError(e.getMessage());
			}
		}

		/**
		 * Searches and displays tasks that contain the provided keyword.
		 *
		 * @param userInput The full user input with the find command and keyword.
		 */
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


		/**
		 * Takes care of marking or unmarking of a task as completed.
		 *
		 * @param echo_word The full user input with the mark/unmark command and index.
		 */
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

		/**
		 * Checks if index provided is within bounds of the task list.
		 *
		 * @param task_index The task index to check.
		 * @throws IndexOutOfBoundsException If index < 0 or index >= list size.
		 */
		private static void checkOutOfBounds(int task_index) {
			if (task_index < 0) {
				throw new IndexOutOfBoundsException(ui.outOfBoundsInputErrorTooSmall());
			}
			if (task_index >= todo_list.size()) {
				throw new IndexOutOfBoundsException(ui.outOfBoundsInputErrorTooBig());
			}
		}

		/**
		 * Checks for duplicate marking/unmarking tries and updates the task as required.
		 * Wrapper function for individual mark/unmarking check functions.
		 * @param echo_word The full user input containing "mark"/"unmark".
		 * @param task_index The index of the task to be marked/unmarked.
		 * @throws DuplicateMarkingException If task's isDone state is already True.
		 */
		private static void checkDuplicate(String echo_word, int task_index) throws DuplicateMarkingException {
			// Checking for duplicate marking or unmarking
			if (echo_word.startsWith("mark")) {
				checkDuplicateMark(task_index);
			} else {
				checkDuplicateUnmark(task_index);
			}
		}

		/**
		 * Based on command type, adds a new task to the list of tasks.
		 *
		 * @param echo_word The full user input with the task description.
		 * @param command The command type (e.g., "todo, "deadline", "event").
		 */
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
				LocalDate deadlineParsed;
				try {
					deadlineParsed = Parser.parseDate(deadlineParts[1]);
				} catch (DateTimeParseException e) {
					ui.showExceptionError(ui.showIndent() + "Invalid date format! You've gotta use yyyy-MM-dd...");
					return;
				}
				if (!ui.checkValidDate(deadlineParsed)) {
					ui.showLine();
					return;
				}
				String deadlineFormatted = Parser.formatDate(deadlineParsed);
				t = todo_list.addDeadline(deadlineParts[0], deadlineFormatted);
				break;
			case "event":
				String[] eventParts = Parser.getEventParts(echo_word);
				LocalDate fromDate;
				LocalDate toDate;
				try {
					fromDate = Parser.parseDate(eventParts[1]);
					toDate = Parser.parseDate(eventParts[2]);
				} catch (DateTimeParseException e) {
					ui.showExceptionError(ui.showIndent() + "Invalid date format! You've gotta use yyyy-MM-dd...");
					return;
				}
				if (!ui.checkValidEventDate(fromDate, toDate)) {
					return;
				}
				String fromFormatted = Parser.formatDate(fromDate);
				String toFormatted = Parser.formatDate(toDate);
				t = todo_list.addEvent(eventParts[0], fromFormatted, toFormatted);
				break;
			default:
				ui.showInvalidCommand();
				return;
			}

			ui.showTaskAdded(t, todo_list.size());
		}

		/**
		 * Checks if the task list is empty.
		 *
		 * @throws EmptyListException If task list is empty.
		 */
		private static void checkEmptyList() throws EmptyListException {
			if (todo_list.isEmpty()) {
				throw new EmptyListException(ui.emptyInputError());
			}
		}

		/**
		 * Checks for duplicate unmark tries and updates the tasks as required.
		 *
		 * @param task_index The index of the task.
		 * @throws DuplicateMarkingException If task is already unmarked.
		 */
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

		/**
		 * Checks for duplicate mark tries and updates the tasks as required.
		 *
		 * @param task_index The index of the task.
		 * @throws DuplicateMarkingException If task is already marked.
		 */
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

		/**
		 * Confirms if user has provided correct number of arguments of strictly 2.
		 *
		 * @param echo_word The full user input.
		 * @throws NumArgsException If number of arguments provided == 1 or > 2.
		 */
		private static void checkCorrectNumArgs(String echo_word) throws NumArgsException {
			if (Parser.getNumUserInput(echo_word) == 1) {
				throw new NumArgsException(ui.missingIndexError());
			} else if (Parser.getNumUserInput(echo_word) > 2) {
				throw new NumArgsException(ui.excessiveInputError());
			}
		}
	}