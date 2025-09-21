package zeus.storage;

import zeus.tasks.Deadline;
import zeus.tasks.Event;
import zeus.tasks.Task;
import zeus.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StorageFile {
	private static String filePath;

	public StorageFile(String filePath) {
		StorageFile.filePath = filePath;
	}

	public ArrayList<Task> loadTasks() {
		ArrayList<Task> tasks = new ArrayList<>();
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				return tasks;
			}

			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Task t = parseTask(line);
				if (t != null) {
					tasks.add(t);
				}
			}

			sc.close();
		} catch (IOException e) {
			System.out.println("Error loading tasks: " + e.getMessage());
		}
		return tasks;
	}

	private Task parseTask(String line) {
		try {
			// Do trimming due to spaces in between "|"
			// E.g. T | 1 | read book
			String[] seg = line.split("\\|");
			String task_type = seg[0].trim();
			boolean isDone = seg[1].trim().equals("1");

			switch (task_type) {
			case "T":
				Task todo = new Todo(seg[2].trim());
				todo.isDone = isDone;
				return todo;
			case "D":
				Task deadline = new Deadline(seg[2].trim(), seg[3].trim());
				deadline.isDone = isDone;
				return deadline;
			case "E":
				Task event = new Event(seg[2].trim(), seg[3].trim(), seg[4].trim());
				event.isDone = isDone;
				return event;
			}
		} catch (Exception e) {
			System.out.println("Ignoring corrupted line: " + line);
			return null;
		}
		return null;
	}

	public void saveTasks(ArrayList<Task> todo_list) {
		try {
			File dir = new File("./data");
			if (!dir.exists()) {
				// Checks if directory "./data" is present
				if (!dir.mkdirs()) {
					System.out.println("Failed to create directories: " + dir.getAbsolutePath());
					return;
				}
			}

			FileWriter fw = new FileWriter(StorageFile.filePath);
			for (Task task : todo_list) {
				// Write in line by line from ArrayList into data file, ensures persistence
				fw.write(task.toSaveFormat() + System.lineSeparator());
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Error saving tasks: " + e.getMessage());
		}
	}
}
