public abstract class Game {
    //TODO: finish abstract method
    public AllGamesRecord playAll(){// Initialize the record holder for all games
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        GameRecord gameRecord = play();   // Play a game and get the result as a GameRecord
        allGamesRecord.add(gameRecord);
        // Loop until playNext() returns false
        while (playNext()) {
            gameRecord = play();   // Play a game and get the result as a GameRecord
            allGamesRecord.add(gameRecord);   // Add the result to AllGamesRecord
        }
        return allGamesRecord;

    };

    public abstract GameRecord play();

    public abstract boolean playNext();
}
