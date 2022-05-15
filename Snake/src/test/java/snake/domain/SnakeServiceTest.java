package snake.domain;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SnakeServiceTest {
    
    SnakeService service;
    FakeUserDao userDao;
    FakeScoreDao scoreDao;
    
    @Before
    public void setUp() {
        this.userDao = new FakeUserDao();
        this.scoreDao = new FakeScoreDao();
        this.service = new SnakeService(this.userDao, this.scoreDao);
    }
    
    @Test
    public void canCreateSingleUser() {
        assertEquals(UserOperationResult.SUCCESS, service.createUser("test"));
        assertEquals(1, this.userDao.getAll().size());
    }
    
    @Test
    public void cannotCreateUserWithTooShortName() {
        assertEquals(UserOperationResult.NAME_TOO_SHORT, service.createUser("t"));
    }
    
    @Test
    public void cannotCreateUserWithSameNameTwice() {
        service.createUser("test");
        
        assertEquals(UserOperationResult.NAME_TAKEN, service.createUser("test"));
    }
    
    @Test
    public void userCreationReturnsInternalErrorUponException() {
        assertEquals(UserOperationResult.INTERNAL_ERROR, service.createUser("error"));
    }
    
    @Test
    public void canLogInToCreatedUser() {
        service.createUser("user");
        
        assertTrue(service.login("user"));
    }
    
    @Test
    public void afterLoggingInLoggedInUserChanges() {
        service.createUser("user");
        service.login("user");
        
        assertEquals("user", service.getLoggedInUser().getUsername());
    }
    
    @Test
    public void canRenameUser() {
        service.createUser("test");
        service.renameUser("test", "newname");
        
        assertEquals("newname", service.getAllUsernames().get(0));
    }
    
    @Test
    public void canSetUserColor() {
        service.createUser("test");
        service.setUserColor("test", Color.RED);
        service.login("test");
        
        assertEquals(Color.RED, service.getLoggedInUser().getColor());
    }
    
    @Test
    public void canFindUpdatedUserScores() {
        service.createUser("test");
        service.addScore("test", 0);
        
        assertEquals(1, service.getUserScores("test").size());
    }

    @Test
    public void canFindUdpatedAllScores() {
        service.createUser("test1");
        service.createUser("test2");
        service.addScore("test1", 1);
        service.addScore("test2", 2);
        
        assertEquals(2, service.getAllScores().size());
    }
    
    @Test
    public void canFindNTopScores() {
        service.createUser("test1");
        service.createUser("test2");
        service.addScore("test1", 1);
        service.addScore("test1", 2);
        service.addScore("test2", 3);
        
        assertEquals(2, service.getTopScores(2).size());
    }
}
