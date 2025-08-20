import java.util.*;

public class WitBot {

    public static void main(String[] args) {
        //        greeting user
        String tab = "\t____________________________________________________________";
        System.out.println(tab);
        String logo = "       __          ___   _      \n" +
                "       \\ \\        / / | (_) |_   \n" +
                "        \\ \\  /\\  / /  | | | __|  \n" +
                "         \\ \\/  \\/ /   | | | |_   \n" +
                "          \\__/\\__/    |_|  \\__|  \n";
        System.out.println("\tHey there human (I hope you are one...)! I'm WitBot - your unconventionally helpful sidekick!\n" + logo);
        System.out.println("\tWhat idea, plan or meme do you have on mind today?");
        System.out.println(tab);


            //        echos user's input
            Scanner sc = new Scanner(System.in);
            String echo_word;
            ArrayList<String> todo_list = new ArrayList<>();
            int task_index = 1;

        while (true) {
            echo_word = sc.nextLine();
            if (echo_word.equals("bye")) {
                break;
            } else if (echo_word.isEmpty()) {
//                empty input check
                System.out.println("\tOops! You've gotta input something~");
                System.out.println(tab);
            } else if (echo_word.equals("list")) {
                System.out.println(tab);
                if (todo_list.isEmpty()) {
                    System.out.println("\tYou're free for the day dear!");
                    System.out.println(tab);
                    continue;
                }
                for (String task : todo_list) {
                    System.out.println("\t" + task_index + ". " + task);
                    task_index++;
                }
                System.out.println(tab);
            } else {
                todo_list.add(echo_word);
                System.out.println(tab);
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