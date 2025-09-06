package zeus.tasks;

public class Task {
	protected String description;
	public boolean isDone;

	public Task(String description) {
		this.description = description;
		this.isDone = false;
	}

	public String getStatusIcon() {
		return (isDone ? "X" : " ");
	}

	public String toString() {
		return "[" + getStatusIcon() + "] " + description;
	}
}

