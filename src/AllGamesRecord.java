import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllGamesRecord {
    List<GameRecord> gameRecordList= new ArrayList<>();
    private double averageRecord = 0;
   //Done: add(GameRecord)-- adds a GameRecord to the AllGamesRecord
    public void add(GameRecord gameRecord){
        double totalScore = averageRecord * gameRecordList.size();
        gameRecordList.add(gameRecord);
        totalScore += gameRecord.score;
        averageRecord = totalScore/gameRecordList.size();


    }
   //Done: average()-- returns the average score for all games added to the record
    public double average(){
        return averageRecord;
    }
    //Done: average(playerId) -- returns the average score for all games of a particular player
    public double average(String playerId){
        double totalForID = 0;
        int gamesPlayed = 0;
        for(GameRecord gameRecord : gameRecordList){
            if(playerId.equals(gameRecord.playerId)){
                gamesPlayed++;
                totalForID += gameRecord.score;
            }
        }
        return totalForID/gamesPlayed;
    }
    //Done: highGameList(n)-- returns a sorted list of the top n scores including player and score. This method should use the Collections class to sort the game instances.
    public List<GameRecord> highGameList(int n){
        List<GameRecord> topNList = new ArrayList<>();
        Collections.sort(gameRecordList);
        for(int i = 0; i < n; i++){
            topNList.add(gameRecordList.get(i));
        }
        return topNList;
    }
    //Done: highGameList(playerId, n)-- returns a sorted list of the top n scores for the specified player.. This method should use the Collections class to sort the game instances.
    public List<GameRecord> highGameList(int n, String playerId){
        List<GameRecord> topNList = new ArrayList<>();
        Collections.sort(gameRecordList);
        int index = 0;
        while(topNList.size() < n && index < gameRecordList.size()){
            GameRecord curGameRecord = gameRecordList.get(index);
            if(curGameRecord.playerId.equals(playerId)){
                topNList.add(curGameRecord);
            }
            index++;
        }
        return topNList;
    }

    @Override
    public String toString() {
        return "AllGamesRecord{" +
                "gameRecordList=" + gameRecordList +
                ", averageRecord=" + averageRecord +
                '}';
    }
}
