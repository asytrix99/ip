package main.java;

public class Task {
	//    using class to represent object (recommended by textbook)
	protected String description;
	protected boolean isDone;

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

