package snake.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.stream.Collectors;
import snake.domain.Score;
import snake.domain.User;

/**
 * A score data access object that reads the scores from a CSV file.
 */
public class CsvScoreDao implements ScoreDao {
    private LinkedList<Score> scores; // Always sorted in descending order.
    private String filePath;
    
    public CsvScoreDao(String path) {
        this.scores = new LinkedList<Score>();
        this.filePath = path;
        
        try {
            File file = new File(path);
            file.createNewFile();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    this.scores.add(new Score(Integer.parseInt(parts[0]), UUID.fromString(parts[1])));
                }
            }
        } catch (IOException e) {
            System.err.println("An IO error occurred while reading a file:");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Add a score.
     * 
     * @param score The score to be added.
     * @throws Exception
     */
    @Override
    public void add(Score score) throws Exception {
        if (this.scores.isEmpty() || score.getValue() < this.scores.getLast().getValue()) {
            this.scores.addLast(score);
        } else {
            ListIterator<Score> it = this.scores.listIterator();
            while (it.hasNext()) {
                Score s = it.next();
                if (s.getValue() < score.getValue()) {
                    it.previous();
                    it.add(score);
                    break;
                }
            }
        }
        
        this.save();
    }

    /**
     * @return A list of all the scores, in descending order.
     */
    @Override
    public List<Score> getAll() {
        return this.scores;
    }

    /**
     * @param user The user whose scores are to be returned.
     * @return A list of the user's scores, in descending order.
     */
    @Override
    public List<Integer> getUserScores(User user) {
        return this.scores.stream()
                .filter(s -> s.getOwnerId().equals(user.getId()))
                .map(s -> s.getValue())
                .collect(Collectors.toList());
    }
    
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(this.filePath))) {
            for (Score s : this.scores) {
                writer.write(s.getValue() + ";" + s.getOwnerId() + "\n");
            }
        }
    }
    
}
