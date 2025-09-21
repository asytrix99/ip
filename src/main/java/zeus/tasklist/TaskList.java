package zeus.tasklist;

import zeus.tasks.Deadline;
import zeus.tasks.Event;
import zeus.tasks.Task;
import zeus.tasks.Todo;

import java.util.ArrayList;

public class TaskList {

	private final ArrayList<Task> todo_list;

	public TaskList() {
		this.todo_list = new ArrayList<>();
	}
	public TaskList(ArrayList<Task> loadedTasks) {
		this.todo_list = loadedTasks;
	}

	public Task addTodo(String description) {
		Task t = new Todo(description);
		todo_list.add(t);
		return t;
	}

	public Task addDeadline(String description, String by) {
		Task t = new Deadline(description, by);
		todo_list.add(t);
		return t;
	}

	public Task addEvent(String description, String from, String to) {
		Task t = new Event(description, from, to);
		todo_list.add(t);
		return t;
	}

	public void deleteTask(int index) {
		todo_list.remove(index);
	}

	public Task get(int index) {
		return todo_list.get(index);
	}

	public int size() {
		return todo_list.size();
	}

	public boolean isEmpty() {
		return todo_list.isEmpty();
	}

	public ArrayList<Task> returnAllTasks() {
		return todo_list;
	}
}
