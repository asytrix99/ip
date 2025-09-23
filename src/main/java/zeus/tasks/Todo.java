package zeus.tasks;

/**
 * Indicates a task of type {@code Todo}.
 * <p>
 * A task of this type is basic, with only description and completion status.
 */
public class Todo extends Task {

	/**
	 * Creates a new {@code Todo} task with specific description.
	 * The task is initialised as not completed.
	 *
	 * @param description The description of the todo task.
	 */
	public Todo (String description) {
		super(description);
	}

	/**
	 * Returns a visualised string representation of this {@code Todo} task,
	 * inclusive of its status symbol and description.
	 *
	 * @return A formatted string representation of this {@code Todo} task.
	 */
	@Override
	public String toString() {
		return "\t\t[T]" + super.toString();
	}

	/**
	 * Returns a text file based string representation of {@code Todo} task
	 * that will be saved into the text file for persistence.
	 * <p>
	 * The formatting is:
	 * <pre>
	 * T | {status} | {description}
	 * </pre>
	 * where {@code status} is {@code 1} if done, else {@code 0}.
	 *
	 * @return A string representation of this task for file saving.
	 */
	@Override
	public String toSaveFormat() {
		return "T | " + getStatusIconSave() + " | " + description;
	}
}
