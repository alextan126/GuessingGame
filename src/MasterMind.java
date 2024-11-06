import java.util.*;

public class MasterMind extends GuessingGame {
    private static final String COLORS = "RGBYOP"; // Allowed colors
    private static final int CODE_LENGTH = 4; // Length of the secret code
    private String secretCode;

    public MasterMind() {
        generateSecretCode();
    }

    // Generates a random secret code
    @Override
    public void generateSecretCode() {
        secretCode = super.generateSecretCode(COLORS,CODE_LENGTH);
    }

    // Prompts the user for a guess and returns it
    @Override
    public String getGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your guess (e.g., RGRY): ");
        System.out.println(secretCode);
        String guess = scanner.nextLine().toUpperCase().trim();

        // Validate the guess
        while (!isValidGuess(guess)) {
            System.out.println("Invalid guess. Please enter a valid guess using the letters R, G, B, Y, O, P.");
            System.out.print("Enter your guess (e.g., RGRY): ");
            guess = scanner.nextLine().toUpperCase().trim();
        }

        return guess;
    }

    // Validates the user's guess
    private boolean isValidGuess(String guess) {
        if (guess.length() != CODE_LENGTH) {
            return false;
        }
        for (char c : guess.toCharArray()) {
            if (!new String(COLORS).contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public GameRecord play() {
        System.out.println("Welcome to MasterMind!");
        System.out.println("Guess the secret code. Possible colors are: R, G, B, Y, O, P.");
        System.out.println("The code has 4 colors. Good luck!");

        int attempts = 0;

        while (true) {
            String guess = getGuess();
            attempts++;

            int exactMatches = countExactMatches(guess);
            int partialMatches = countPartialMatches(guess) - exactMatches;

            System.out.println("Feedback: " + exactMatches + " exact, " + partialMatches + " partial.");

            if (exactMatches == CODE_LENGTH) {
                System.out.println("Congratulations! You guessed the secret code in " + attempts + " attempts.");
                break;
            }
        }

        return new GameRecord(101 - attempts, "Player");
    }

    //reset the game
    private void reset() {
        generateSecretCode();
    }

    @Override
     public boolean playNext() {
        boolean ifNext = super.playNext();
        if (ifNext) reset();
        return ifNext;
    }

    // Counts exact matches between the guess and the secret code
    private int countExactMatches(String guess) {
        int count = 0;
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    // Counts partial matches (ignoring exact matches)
    private int countPartialMatches(String guess) {
        int count = 0;
        Map<Character, Integer> secretCodeMap = buildCharCountMap(secretCode);
        Map<Character, Integer> guessMap = buildCharCountMap(guess);

        for (char color : COLORS.toCharArray()) {
            if (guessMap.containsKey(color) && secretCodeMap.containsKey(color)) {
                count += Math.min(guessMap.get(color), secretCodeMap.get(color));
            }
        }

        return count;
    }

    // Helper method to build a frequency map of characters
    private Map<Character, Integer> buildCharCountMap(String input) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : input.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }


    public static void main(String[] args) {
        MasterMind masterGame = new MasterMind();
        AllGamesRecord record = masterGame.playAll();
        System.out.println(record.highGameList(2));
        System.out.println(record.average());
        System.out.println(record);
    }
}
