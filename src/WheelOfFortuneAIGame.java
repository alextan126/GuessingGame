import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WheelOfFortuneAIGame extends WheelOfFortune{
    List<WheelOfFortunePlayer> playerList;
    WheelOfFortunePlayer wheelOfFortunePlayer;
    int indexOfPlayer = 0;
    int indexOfGame = 0;
    List<String> phraseList;
    public WheelOfFortuneAIGame(){
        playerList = new ArrayList<>();
        playerList.add(new DumbestAI());
        loadPhrases();
        wheelOfFortunePlayer = playerList.get(indexOfPlayer);
    }

    public WheelOfFortuneAIGame(WheelOfFortunePlayer player){
        this.playerList = new ArrayList<>();
        this.playerList.add(player);
        loadPhrases();
        wheelOfFortunePlayer = playerList.get(indexOfPlayer);
    }

    public WheelOfFortuneAIGame(List<WheelOfFortunePlayer> playerList){
        assert playerList != null : "PlayerList cannot be null";
        this.playerList = new ArrayList<>(playerList);
        loadPhrases();
        wheelOfFortunePlayer = playerList.get(indexOfPlayer);
    }

    //add function to add ais
    public void add(WheelOfFortunePlayer player){
        this.playerList.add(player);
    }

    // All AI players play the same phraseList
    private void loadPhrases() {
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error loading phrases: " + e.getMessage());
            phraseList = new ArrayList<>();  // Initialize with empty list if loading fails
        }
    }


    // rewrite the random phrase so each phrase is played for each player
    @Override
    public void randomPhrase() {
        // Set the phrase for the current game based on index
        wheelOfFortunePlayer = playerList.get(indexOfPlayer);
        phrase = phraseList.get(indexOfGame);
        System.out.println("A secret phrase has been generated for player: " + wheelOfFortunePlayer);

    }

    @Override
    public char getGuess() {
        return wheelOfFortunePlayer.nextGuess();
    }

    @Override
    public GameRecord play() {
        randomPhrase();
        phraseToMap();
        generateHiddenPhrase();
        // Main game loop
        while (characterLeft > 0) {
            char curGuess = getGuess();
            processGuess(curGuess);
        }

        System.out.println("Congrats! AI guessed it! The secret code is: " + hiddenPhrase);

        // Calculate score and record with AI player’s ID
        int score = 26 - dedupSet.size();
        return new GameRecord(score, wheelOfFortunePlayer.playerId());
    }

    @Override
    // check if next availabe and handle index
    public boolean playNext() {
        boolean ifNext = indexOfPlayer < playerList.size() || indexOfGame < phraseList.size();

        if (ifNext) {
            // Reset the game state for the next round
            characterLeft = 0;
            map = new HashMap<>();
            dedupSet = new HashSet<>();
            wheelOfFortunePlayer.reset();
            indexOfGame++;
            if (indexOfGame >= phraseList.size()) {
                indexOfGame = 0;
                indexOfPlayer++;
                if (indexOfPlayer == playerList.size()) {
                    return false;
                }

            }
        }

        return ifNext;
    }

    public static void main(String [] args) {
        DumbestAI dumbestAI = new DumbestAI();
        DumberAI dumberAI = new DumberAI();
        DumbAI dumbAI = new DumbAI();
        WheelOfFortuneAIGame wheelOfFortuneAIGame = new WheelOfFortuneAIGame(dumbestAI);
        wheelOfFortuneAIGame.add(dumberAI);
        wheelOfFortuneAIGame.add(dumbAI);
        AllGamesRecord record = wheelOfFortuneAIGame.playAll();
        System.out.println(record.average("dumbAI"));
        System.out.println(record.highGameList(6));
        System.out.println(record.highGameList(3,"dumbAI"));
        System.out.println(record.gameRecordList.size());  // or call specific functions of record
    }
}
