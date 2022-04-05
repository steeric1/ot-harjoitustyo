package snake.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import snake.domain.SnakeService;

public class SnakeServiceTest {
    
    SnakeService service;
    FakeUserDao userDao;
    
    @Before
    public void setUp() {
        this.userDao = new FakeUserDao();
        this.service = new SnakeService(this.userDao);
    }
    
    @Test
    public void canCreateSingleUser() {
        assertEquals(UserCreationResult.SUCCESS, service.createUser("test"));
        assertEquals(1, this.userDao.getAll().size());
    }
    
    @Test
    public void cannotCreateUserWithTooShortName() {
        assertEquals(UserCreationResult.NAME_TOO_SHORT, service.createUser("t"));
    }
    
    @Test
    public void cannotCreateUserWithSameNameTwice() {
        service.createUser("test");
        
        assertEquals(UserCreationResult.NAME_TAKEN, service.createUser("test"));
    }

}
