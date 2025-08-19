import java.util.*;
import static java.lang.Integer.parseInt;

public class WitBot {

//    using class to represent object (recommended by textbook)
    public static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public String getStatusIcon() {
            return (isDone ? "X" : " ");
        }

        public void print_taskrow(String description) {
            System.out.println("       [" + getStatusIcon() + "] " + description);
        }
    }

    public static void main(String[] args) {
//        greeting user
        String tab = "\t____________________________________________________________";
        System.out.println(tab);
        System.out.println("\tHey there human (I hope you are...)! I'm WitBot - your unconventionally helpful sidekick!");
        System.out.println("\tWhat idea, plan or meme do you have on mind today?");
        System.out.println(tab);

//        echos user's input
        Scanner sc = new Scanner(System.in);
        String echo_word = "";
//        array with class type
        ArrayList<Task> todo_list = new ArrayList<>();
        int task_index = 0;

        while (true) {
            echo_word = sc.nextLine();

            if (echo_word.equals("bye")) {
                break;
            } else if (echo_word.isEmpty()) {
//                empty input check
                System.out.println("Oops! You've gotta input something~");
                System.out.println(tab);
                continue;
            } else if (echo_word.startsWith("mark") || echo_word.startsWith("unmark")){
//                ensuring args == 2
                if (echo_word.split(" ").length == 1) {
                    System.out.println(tab);
                    System.out.println("What are you even referring to? Add an index!");
                    System.out.println(tab);
                    continue;
                } else if (echo_word.split(" ").length > 2) {
                    System.out.println(tab);
                    System.out.println("One task at a time my friend! Input only one digit~");
                    System.out.println(tab);
                    continue;
                }
                System.out.println(tab);

                task_index = parseInt(echo_word.split(" ")[1]) - 1;

                if (echo_word.startsWith("mark")) {
//                    duplicate mark check
                    if (todo_list.get(task_index).isDone) {
                        System.out.println("You've already finished this silly!");
                        System.out.println(tab);
                        continue;
                    } else {
                        System.out.println("Ah! That's amazing, you've got another one crossed out~");
                        todo_list.get(task_index).isDone = true;
                        System.out.println(tab);
                    }
                } else {
//                    duplicate unmark check
                    if (!todo_list.get(task_index).isDone) {
                        System.out.println("I see... Trying to run away from responsibilities? It's already unmarked...");
                        System.out.println(tab);
                        continue;
                    } else {
                        System.out.println("Awh, it's alright, you can work on this next time. Keep up!");
                        todo_list.get(task_index).isDone = false;
                    }
                }
                todo_list.get(task_index).print_taskrow(todo_list.get(task_index).description);
                System.out.println(tab);
            } else if (echo_word.equals("list")) {
                //            listing current tasks
                int list_counter = 1;
                System.out.println(tab);
                if (todo_list.isEmpty()) {
                    System.out.println("Good on ya, you're free for the day!");
                }
                for (Task task : todo_list) {
                    System.out.println("\t" + list_counter + ". [" + task.getStatusIcon() + "] " + task.description);
                    list_counter++;
                }
                System.out.println(tab);
            } else {
                System.out.println(tab);
//                initialize task & add to todo_list
                Task t = new Task(echo_word);
                todo_list.add(t);
                t.description = echo_word;
                System.out.println("\tadded: " + echo_word);
                System.out.println(tab);
            }
        }

//        says bye to user
        System.out.println(tab);
        System.out.println("\tAwh... so fast? Alright then, hope to see you again soon!");
        System.out.println(tab);
    }
}