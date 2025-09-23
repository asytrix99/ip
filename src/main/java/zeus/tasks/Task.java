package zeus.tasks;

/**
 * Indicates an abstract task in the ZeusBot application.
 * <p>
 * Each {@code Task} consists of a description and a completion status, {@code isDone}.
 * Consequential subclasses (e.g., Todo, Deadline, Event) are required to
 * include the {@link #toSaveFormat()} method for persistence.
 */
public abstract class Task {
	/** The description of the task. */
	public String description;

	/** The status of completion of the task. {@code True} if done, else {@code False}. */
	public boolean isDone;

	/**
	 * Creates a new task with provided description.
	 * The task is initialised as not completed.
	 *
	 * @param description The description of the task.
	 */
	public Task(String description) {
		this.description = description;
		this.isDone = false;
	}

	/**
	 * Returns a status symbol to display.
	 *
	 * @return {@code "X"} if task is marked as done,
	 * else a single whitespace character {@code " "}.
	 */
	public String getStatusIcon() {
		return (isDone ? "X" : " ");
	}

	/**
	 * Returns a symbol for text file saving purposes.
	 *
	 * @return {@code "1"} if task is done, else {@code "0"}.
	 */
	public String getStatusIconSave() {
		return (isDone ? "1" : "0");
	}

	/**
	 * Returns a visualised string representation of the task,
	 * inclusive of its status symbol and description.
	 *
	 * @return A formatted string representation of this task.
	 */
	@Override
	public String toString() {
		return "[" + getStatusIcon() + "] " + description;
	}

	/**
	 * Returns a text file based string representation of task
	 * that will be saved into the text file for persistence.
	 * <p>
	 * Every subclass will override this function and define their own save formatting.
	 *
	 * @return A string representation of this task for file saving.
	 */
	public abstract String toSaveFormat();
}

