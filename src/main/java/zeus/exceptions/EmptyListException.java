package zeus.exceptions;

/**
 * Exception thrown to indicate operational attempt on an empty todo list.
 */
public class EmptyListException extends Exception {
	/**
	 * Creates a new {@code EmptyListException} with specified message.
	 *
	 * @param message The message about the error.
	 */
	public EmptyListException(String message) {
		super(message);
	}
}
