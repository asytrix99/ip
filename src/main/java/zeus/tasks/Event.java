package zeus.tasks;

public class Event extends Task {
	protected String from;
	protected String to;

	/**
	 * Creates a new {@code Event} task with specific description and period (to and from).
	 * The task is initialised as not completed.
	 *
	 * @param description The description of the event task.
	 */
	public Event (String description, String from, String to) {
		super(description);
		this.from = from;
		this.to = to;
	}

	/**
	 * Returns a visualised string representation of this {@code Event} task,
	 * inclusive of its status symbol, description and period (to and from) of event.
	 *
	 * @return A formatted string representation of this {@code Event} task.
	 */
	@Override
	public String toString() {
		return "\t\t[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
	}

	/**
	 * Returns a text file based string representation of {@code Event} task
	 * that will be saved into the text file for persistence.
	 * <p>
	 * The formatting is:
	 * <pre>
	 * T | {status} | {description} | {from} | {to}
	 * </pre>
	 * where {@code status} is {@code 1} if done, else {@code 0}.
	 *
	 * @return A string representation of this task for file saving.
	 */
	@Override
	public String toSaveFormat() {
		return "E | " + getStatusIconSave() + " | " + description + " | " + from + " | "  + to;
	}
}
