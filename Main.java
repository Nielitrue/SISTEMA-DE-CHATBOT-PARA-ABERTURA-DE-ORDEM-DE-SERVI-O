import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatBot chatBot = new ChatBot(scanner);

        chatBot.iniciar();
        scanner.close();
    }
}
