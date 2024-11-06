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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the objects are the same
        if (obj == null || getClass() != obj.getClass()) return false; // Check if obj is not null and of the same class
        GameRecord other = (GameRecord) obj; // Cast obj to GameRecord
        return Double.compare(this.score, other.score) == 0; // Compare scores
    }

}
