import java.util.Random;
import java.util.Scanner;

public abstract class GuessingGame<T> extends Game{
    public String secretCode;
    // Handles play-again logic
    public boolean playNext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (T/F): ");
        String input = scanner.nextLine().trim().toUpperCase();

        while (!input.equals("T") && !input.equals("F")) {
            System.out.println("Invalid input. Please enter 'T' for True or 'F' for False.");
            System.out.print("Do you want to play again? (T/F): ");
            input = scanner.nextLine().trim().toUpperCase();
        }

        return input.equals("T");
    }

    public String generateSecretCode(String source, int length) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(source.charAt(random.nextInt(source.length())));
        }
        secretCode = codeBuilder.toString();
        return secretCode;
    }

    public void generateSecretCode(){

    };
    public abstract T getGuess();
}
