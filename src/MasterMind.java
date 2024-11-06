import java.util.*;

public class MasterMind extends GuessingGame {
    private static final char[] COLORS = {'R', 'G', 'B', 'Y', 'O', 'P'}; // Allowed colors
    private static final int CODE_LENGTH = 4; // Length of the secret code
    private String secretCode;

    public MasterMind() {
        generateSecretCode();
    }

    // Generates a random secret code
    @Override
    public void generateSecretCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(COLORS[random.nextInt(COLORS.length)]);
        }
        secretCode = codeBuilder.toString();
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

    @Override
    public boolean playNext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (T/F): ");
        String input = scanner.nextLine().trim().toUpperCase();

        while (!input.equals("T") && !input.equals("F")) {
            System.out.println("Invalid input. Please enter 'T' for True or 'F' for False.");
            System.out.print("Do you want to play again? (T/F): ");
            input = scanner.nextLine().trim().toUpperCase();
        }

        if(input.equals("T")){
            reset();
        }
        return input.equals("T");
    }

    //reset the game
    private void reset() {
        generateSecretCode();
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

        for (char color : COLORS) {
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
