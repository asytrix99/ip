package zeus.exceptions;

/**
 * Exception thrown to indicate argument count provided by user
 * does not match expected count.
 */
public class NumArgsException extends Exception {
	/**
	 * Creates a new {@code NumArgsException} with specified message.
	 *
	 * @param message The message about the error.
	 */
	public NumArgsException(String message) {
		super(message);
	}
}
