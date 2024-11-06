import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DumberAI implements WheelOfFortunePlayer{
    //dumber AI takes a random guess and will not repeat guess
    static Set<Character> previousGuess = new HashSet<>();

    //initialize random
    static Random RANDOM = new Random();
    final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public char nextGuess() {
        return guess();
    }

    public char guess(){
        char curGuess = generateRandomCharacter();
        while(previousGuess.contains(curGuess)){
            curGuess = generateRandomCharacter();
        }
        previousGuess.add(curGuess);
        return curGuess;
    }

    public Character generateRandomCharacter(){
        int randIndex = RANDOM.nextInt(ALPHABET.length());
        Character randChar = ALPHABET.charAt(randIndex);
        randChar = Character.toLowerCase(randChar);
        return randChar;
    }

    @Override
    public String playerId() {
        return "dumberAI";
    }

    @Override
    public void reset() {
        previousGuess = new HashSet<>();
    }

}
