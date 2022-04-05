package snake.dao;

import java.util.List;
import snake.domain.User;

public interface UserDao {
    public void add(User user) throws Exception;
    public List<User> getAll();
}
