package snake.dao;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;
import javafx.scene.paint.Color;
import snake.domain.Score;
import snake.domain.User;

public class CsvScoreDaoTest {
    private static final String PATH = "test-scores.csv";
    CsvScoreDao dao;
    
    @Before
    public void setUp() {
        this.resetFile();
        dao = new CsvScoreDao(PATH);
    }
    
    @Test
    public void afterAddingScoreItAppearsOnList() {
        UUID id = UUID.randomUUID();
        int value = new Random().nextInt();
        try {
            dao.add(new Score(value, id));
        } catch (Exception e) {}
        
        assertTrue(dao.getAll().stream()
                .filter(s -> s.getOwnerId().equals(id) && s.getValue() == value)
                .findAny()
                .isPresent());
    }
    
    @Test
    public void afterAddingScoreItCanBeFoundByUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "test", Color.ALICEBLUE);
        int value = new Random().nextInt();
        try {
            dao.add(new Score(value, id));
        } catch (Exception e) {}
        
        assertTrue(dao.getUserScores(user).contains(value));
    }
    
    @Test
    public void scoresAreInDescendingOrder() {
        UUID id = UUID.randomUUID();
        for (int i = 0; i < 20; ++i) {
            try {
                dao.add(new Score(i, id));
            } catch (Exception e) {}
        }
        
        List<Score> scores = dao.getAll();
        for (int i = 0; i < scores.size() - 1; ++i) {
            assertTrue(scores.get(i).getValue() > scores.get(i + 1).getValue());
        }
    }
    
    private void resetFile() {
        try {
            File f = new File(PATH);
            if (!f.createNewFile()) {
                PrintWriter pw = new PrintWriter(PATH);
                pw.close();
            }
        } catch (Exception e) {}
    }
}
