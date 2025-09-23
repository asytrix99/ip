package zeus.tasklist;

import zeus.tasks.Deadline;
import zeus.tasks.Event;
import zeus.tasks.Task;
import zeus.tasks.Todo;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

/**
 * Represents a list of {@code Task} objects handled by ZeusBot.
 * <p>
 * Offers methods to add, delete, return and filter tasks.
 * Works for various task types of {@code Todo}, {@code Deadline} and {@code Event}.
 */
public class TaskList {

	private final ArrayList<Task> todo_list;

	/**
	 * Creates an empty {@code TaskList}.
	 */
	public TaskList() {
		this.todo_list = new ArrayList<>();
	}

	/**
	 * Creates a {@code TaskList} with preloaded tasks provided.
	 *
	 * @param loadedTasks The list of tasks to initialise with.
	 */
	public TaskList(ArrayList<Task> loadedTasks) {
		this.todo_list = loadedTasks;
	}

	/**
	 * Returns a filtered todo list containing the keyword provided in the description.
	 *
	 * @param keyWord The queried keyword in the task description
	 * @param todo_list The todo list to filter from
	 * @return A new {@code ArrayList<Task>} of tasks with the existing keyword.
	 */
	public ArrayList<Task> getFilteredList(String keyWord, TaskList todo_list) {
		return todo_list.returnAllTasks().stream()
				.filter((task) -> task.description.contains(keyWord))
				.collect(toCollection(ArrayList<Task>::new));
	}

	/**
	 * Adds a new {@code Todo} task to the list.
	 *
	 * @param description The description of the todo task.
	 * @return The created {@code Todo} task.
	 */
	public Task addTodo(String description) {
		Task t = new Todo(description);
		todo_list.add(t);
		return t;
	}

	/**
	 * Adds a new {@code Deadline} task to the list.
	 *
	 * @param description The description of the deadline task.
	 * @return The created {@code Deadline} task.
	 */
	public Task addDeadline(String description, String by) {
		Task t = new Deadline(description, by);
		todo_list.add(t);
		return t;
	}
	/**
	 * Adds a new {@code Event} task to the list.
	 *
	 * @param description The description of the event task.
	 * @return The created {@code Event} task.
	 */
	public Task addEvent(String description, String from, String to) {
		Task t = new Event(description, from, to);
		todo_list.add(t);
		return t;
	}

	/**
	 * Deletes a task from the list according to index.
	 *
	 * @param index The task index to delete.
	 */
	public void deleteTask(int index) {
		todo_list.remove(index);
	}

	/**
	 * Retrieves the task at the specified index.
	 *
	 * @param index The task index to retrieve.
	 * @return The {@code Task} at given index.
	 */
	public Task get(int index) {
		return todo_list.get(index);
	}

	/**
	 * Returns the task count in the list.
	 *
	 * @return The number of tasks in the list.
	 */
	public int size() {
		return todo_list.size();
	}

	/**
	 * Checks if the task list is empty.
	 *
	 * @return If list is empty, returns {@code True}, else {@code False}.
	 */
	public boolean isEmpty() {
		return todo_list.isEmpty();
	}

	/**
	 * Returns the given task list.
	 *
	 * @return An {@code ArrayList<Task>} containing all tasks.
	 */
	public ArrayList<Task> returnAllTasks() {
		return todo_list;
	}
}
