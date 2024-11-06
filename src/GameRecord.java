import java.util.Comparator;

public class GameRecord implements Comparable<GameRecord> {
    double score;
    String playerId;

    public GameRecord(double score, String playerId){
        this.score = score;
        this.playerId = playerId;
    }

    @Override
    public int compareTo(GameRecord other) {
        if(this.score == other.score)return 0;
        return this.score > other.score? -1 : 1;
    }

    @Override
    public String toString(){
        return "GameRecord{" +
                "score=" + score +
                "playerId =" + playerId +
                '}';
    }
}
