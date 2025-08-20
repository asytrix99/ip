import java.util.Scanner;

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

        while (true) {
            //        echos user's input
            Scanner sc = new Scanner(System.in);
            String echo_word;
            echo_word = sc.nextLine();

            if (echo_word.equals("bye")) {
                break;
            } else if (echo_word.isEmpty()) {
//                empty input check
                System.out.println(tab);
                System.out.println("Oops! You've gotta input something~");
                System.out.println(tab);
            } else {
                System.out.println(tab);
                System.out.println("\t" + echo_word);
                System.out.println(tab);
            }
        }
        //        says bye to user
        System.out.println(tab);
        System.out.println("\tAwh... so fast? Alright then, hope to see you again soon!");
        System.out.println(tab);
    }
}