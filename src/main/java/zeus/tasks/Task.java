package zeus.tasks;

public abstract class Task {
	protected String description;
	public boolean isDone;

	public Task(String description) {
		this.description = description;
		this.isDone = false;
	}

	public String getStatusIcon() {
		return (isDone ? "X" : " ");
	}

	public String getStatusIconSave() {
		return (isDone ? "1" : "0");
	}

	public String toString() {
		return "[" + getStatusIcon() + "] " + description;
	}

	public abstract String toSaveFormat();
}

