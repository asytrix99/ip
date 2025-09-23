package zeus.tasks;

public class Deadline extends Task {
	protected String by;

	/**
	 * Creates a new {@code Deadline} task with specific description and deadline.
	 * The task is initialised as not completed.
	 *
	 * @param description The description of the deadline task.
	 */
	public Deadline (String description, String by) {
		super(description);
		this.by = by;
	}

	/**
	 * Returns a visualised string representation of this {@code Deadline} task,
	 * inclusive of its status symbol, description and deadline of task.
	 *
	 * @return A formatted string representation of this {@code Deadline} task.
	 */
	@Override
	public String toString() {
		return "\t\t[D]" + super.toString() + " (by: " + by + ")";
	}

	/**
	 * Returns a text file based string representation of {@code Deadline} task
	 * that will be saved into the text file for persistence.
	 * <p>
	 * The formatting is:
	 * <pre>
	 * T | {status} | {description} | {by}
	 * </pre>
	 * where {@code status} is {@code 1} if done, else {@code 0}.
	 *
	 * @return A string representation of this task for file saving.
	 */
	@Override
	public String toSaveFormat() {
		return "D | " + getStatusIconSave() + " | " + description + " | " + by;
	}
}
