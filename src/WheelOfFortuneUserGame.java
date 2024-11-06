import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class WheelOfFortuneUserGame extends WheelOfFortune{
    Scanner scanner = new Scanner(System.in);
    @Override
    public Character getGuess() {
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
       boolean ifNext = super.playNext();
        if(ifNext){
            characterLeft = 0;
            map = new HashMap<>();
            dedupSet = new HashSet<>();
        }
        return ifNext;
    }

    public static void main(String [] args) {
        WheelOfFortuneUserGame wheelOfFortuneUserGame = new WheelOfFortuneUserGame();
        AllGamesRecord record = wheelOfFortuneUserGame.playAll();
        System.out.println(record.highGameList(2));
        System.out.println(record.average());
        System.out.println(record);

    }
}
