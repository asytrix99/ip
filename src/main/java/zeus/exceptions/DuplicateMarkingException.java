package zeus.exceptions;

/**
 * Exception thrown to indicate attempt to mark or unmark although
 * its {@code isDone} state already reflects the requested action (mark or unmark).
 */
public class DuplicateMarkingException extends Exception {
	/**
	 * Creates a new {@code DuplicateMarkingException} with specified message.
	 *
	 * @param message The message about the error.
	 */
	public DuplicateMarkingException(String message) {
		super(message);
	}
}
