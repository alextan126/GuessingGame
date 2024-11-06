import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class WheelOfFortune extends Game {

    protected StringBuilder hiddenPhrase = new StringBuilder();
    protected String phrase;
    protected int characterLeft = 0;
    protected HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
    protected Set<Character> dedupSet = new HashSet<>();
    protected static Random rand = new Random();


    public abstract char getGuess();

    // Plays a single game and returns a GameRecord
    @Override
    public GameRecord play() {
        randomPhrase();
        phraseToMap();
        generateHiddenPhrase();
        while (characterLeft > 0) {
            char curGuess = getGuess();
            processGuess(curGuess);
        }
        System.out.println("Congrats! You guessed it! The secret code is: " + hiddenPhrase);

        // Example score calculation
        int score = 26 - dedupSet.size();
        return new GameRecord(score, "PlayerId");
    }

    @Override
    public abstract boolean playNext();

    // Randomly selects a phrase from a file
    public void randomPhrase() {
        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }
        int r = rand.nextInt(phraseList.size());
        phrase = phraseList.get(r);
        System.out.println("A secret phrase has been generated.");
    }

    // Maps each character in the phrase to its index positions
    public void phraseToMap() {

        for(int i = 0; i < phrase.length(); i++){
            char cur = phrase.charAt(i);
            //convert to lower case
            cur = Character.toLowerCase(cur);
            if(!map.containsKey(cur)){
                ArrayList<Integer> curList = new ArrayList<>();
                curList.add(i);
                map.put(cur, curList);
            } else{
                ArrayList<Integer> curList = map.get(Character.toLowerCase(cur));
                curList.add(i);
                map.put(cur, curList);
            }
        }
    }

    // Generates the initial hidden phrase with masked characters
    public void generateHiddenPhrase() {
        hiddenPhrase.setLength(0);
        for (int i = 0; i < phrase.length(); i++) {
            char cur = phrase.charAt(i);
            if (Character.isAlphabetic(cur)) {
                hiddenPhrase.append('*');
                characterLeft++;
            } else {
                hiddenPhrase.append(cur);
            }
        }
    }

    // Processes a single guess and updates the hidden phrase if correct
    public void processGuess(char curGuess) {
        curGuess = Character.toLowerCase(curGuess);
        if (dedupSet.contains(curGuess)) {
            System.out.println("This guess has already been made!");
        } else {
            dedupSet.add(curGuess);
            ArrayList<Integer> positions = map.get(curGuess);
            if (positions != null) {
                for (int pos : positions) {
                    hiddenPhrase.setCharAt(pos, phrase.charAt(pos));
                    characterLeft--;
                }
                System.out.println("Good guess! Characters left: " + characterLeft);
                System.out.println("Current phrase: " + hiddenPhrase);
            } else {
                System.out.println("Incorrect guess.");
            }
        }
    }
}
