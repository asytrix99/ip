package zeus.tasks;

public class Todo extends Task {
	public Todo (String description) {
		super(description);
	}

	@Override
	public String toString() {
		return "\t\t[T]" + super.toString();
	}
}
