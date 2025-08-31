package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
			ZeusBot.greetUser();

			Scanner sc = new Scanner(System.in);
			String echo_word = "";
			ArrayList<Task> todo_list = new ArrayList<>(); //array with class type

			while (true) {
				echo_word = sc.nextLine();
				if (echo_word.equals("bye")) {
					break;
				} else if (echo_word.isEmpty()) {
					ZeusBot.warnEmptyInput(); //check empty input
				} else if (echo_word.startsWith("mark") || echo_word.startsWith("unmark")) {
					ZeusBot.handleMarking(echo_word, todo_list); //ensure num args == 2
				} else if (echo_word.equals("list")) {
					ZeusBot.listTasks(todo_list); //list current tasks
				} else
					ZeusBot.addTask(echo_word, todo_list); //add task to list
			}

			ZeusBot.byeUser(); //says bye to user
		}
}