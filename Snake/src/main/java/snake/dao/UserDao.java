package snake.dao;

import java.util.List;
import snake.domain.User;

public interface UserDao {

    /**
     * Add a user to the resource handled by this data access object.
     *
     * @param user The user to add.
     * @throws Exception
     */
    void add(User user) throws Exception;

    /**
     * Get all the users managed by this data access object.
     *
     * @return A list object over the users.
     */
    List<User> getAll();
}
