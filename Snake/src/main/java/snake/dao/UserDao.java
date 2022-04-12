package snake.dao;

import java.util.List;
import snake.domain.User;

public interface UserDao {
    void add(User user) throws Exception;
    List<User> getAll();
    User getByName(String username);
}
