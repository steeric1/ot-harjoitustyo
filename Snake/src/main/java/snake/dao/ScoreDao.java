package snake.dao;

import java.util.List;
import snake.domain.Score;
import snake.domain.User;

public interface ScoreDao {
    void add(Score score) throws Exception;
    List<Score> getAll();
    List<Integer> getUserScores(User user);
}
