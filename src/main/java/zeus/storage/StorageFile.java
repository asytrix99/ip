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

/**
 * Takes charge in persistent storage of tasks using a text file for ZeusBot
 * <p>
 * Offers methods to load tasks from and save them back into a text file.
 * Tasks are formatted into a pre-defined format before the actual saving or loading process.
 * Implementations of formatting are found in {@link Task#toSaveFormat()}.
 */
public class StorageFile {
	private static String filePath;

	/**
	 * Creates a new {@code StorageFile} with given file path.
	 *
	 * @param filePath The storage file path.
	 */
	public StorageFile(String filePath) {
		StorageFile.filePath = filePath;
	}

	/**
	 * Loads tasks from storage text file into local memory.
	 * <p>
	 * Each line in file is parsed into a {@code Task} object
	 * of type {@code Todo}, {@code Deadline} or {@code Event}.
	 * Corrupted or invalid lines are ignored.
	 *
	 * @return An {@code ArrayList<Task>} with all successfully loaded tasks.
	 */
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

	/**
	 * Parses a line from storage text file into a {@code Task} object.
	 * <p>
	 * Example:
	 * <pre>
	 *     "T | 1 | read book" is parsed as type {@code Todo}, {@code isDone} is {@code True}
	 *     and {@code description} is "read book"
	 * </pre>
	 *
	 * @param line The line from storage text file.
	 * @return The parsed in {@code Task} or {@code null} for ignored lines.
	 */
	private Task parseTask(String line) {
		try {
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

	/**
	 * Saves all tasks to storage text file.
	 * Initialises the {@code ./data} directory if it does not exist.
	 * Each line in file is written into a textual format returned by
	 * {@link Task#toSaveFormat()}.
	 *
	 * @param todo_list The list of tasks to save.
	 */
	public void saveTasks(ArrayList<Task> todo_list) {
		try {
			File dir = new File("./data");
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					System.out.println("Failed to create directories: " + dir.getAbsolutePath());
					return;
				}
			}

			FileWriter fw = new FileWriter(StorageFile.filePath);
			for (Task task : todo_list) {
				fw.write(task.toSaveFormat() + System.lineSeparator());
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Error saving tasks: " + e.getMessage());
		}
	}
}
