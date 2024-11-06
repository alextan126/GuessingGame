import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DumbAI implements WheelOfFortunePlayer{
    //dumb AI cheats, peeks at the phrases and guess by frequency
    private List<String> phraseList;
    private HashMap<Character, Integer> map = new HashMap<>();
    private PriorityQueue<Map.Entry<Character, Integer>> maxHeap;
    public DumbAI(){
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error loading phrases: " + e.getMessage());
            phraseList = new ArrayList<>();  // Initialize with empty list if loading fails
        }
        for(String str : phraseList){
            phraseToMap(str);
        }
        maxHeap = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        maxHeap.addAll(map.entrySet());
    }

    private void phraseToMap(String phrase) {

        for(int i = 0; i < phrase.length(); i++){
            char cur = Character.toLowerCase(phrase.charAt(i));
            if (Character.isAlphabetic(cur)) {
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }
        }
    }
    @Override
    public char nextGuess() {
        if (!maxHeap.isEmpty()) {
            return maxHeap.poll().getKey();  // Retrieve and remove the highest frequency character
        }
        return 0;
    }

    @Override
    public String playerId() {
        return "dumbAI";
    }

    @Override
    public void reset() {
        maxHeap.clear();
        maxHeap.addAll(map.entrySet());
    }


}
