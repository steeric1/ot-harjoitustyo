package snake.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import snake.dao.ScoreDao;

public class FakeScoreDao implements ScoreDao {
    private List<Score> scores;
    
    public FakeScoreDao() {
        this.scores = new ArrayList<>();
    }

    @Override
    public void add(Score score) throws Exception {
        this.scores.add(score);
    }

    @Override
    public List<Score> getAll() {
        return this.scores;
    }

    @Override
    public List<Integer> getUserScores(User user) {
        return this.scores.stream()
                .filter(s -> s.getOwnerId().equals(user.getId()))
                .map(s -> s.getValue())
                .collect(Collectors.toList());
    }
    
}
