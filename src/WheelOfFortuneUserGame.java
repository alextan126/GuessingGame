import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class WheelOfFortuneUserGame extends WheelOfFortune{
    Scanner scanner = new Scanner(System.in);
    @Override
    public char getGuess() {
        System.out.print("Enter your guess (one character): ");
        String input = scanner.nextLine().trim();

        // Ensure input is a single alphabetic character
        while (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            System.out.println("Invalid input. Please enter a single alphabetic character.");
            System.out.print("Enter your guess: ");
            input = scanner.nextLine().trim();
        }

        return Character.toLowerCase(input.charAt(0));
    }


    @Override
    public boolean playNext() {
        System.out.print("Do you want to play the next game? (T/F): ");
        String input = scanner.nextLine().trim().toUpperCase();

        // Validate input and prompt if necessary
        while (!input.equals("T") && !input.equals("F")) {
            System.out.println("Invalid input. Please enter 'T' for True or 'F' for False.");
            System.out.print("Do you want to play the next game? (T/F): ");
            input = scanner.nextLine().trim().toUpperCase();
        }
        if(input.equals("T")){
            characterLeft = 0;
            map = new HashMap<>();
            dedupSet = new HashSet<>();
        }
        return input.equals("T");
    }

    public static void main(String [] args) {
        WheelOfFortuneUserGame wheelOfFortuneUserGame = new WheelOfFortuneUserGame();
        AllGamesRecord record = wheelOfFortuneUserGame.playAll();
        System.out.println(record);

    }
}
