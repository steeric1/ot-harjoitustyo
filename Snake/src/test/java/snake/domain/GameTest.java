package snake.domain;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import snake.domain.Game.Position;

public class GameTest {
    Game game;
    
    @Before
    public void setUp() {
        this.game = new Game(14);
    }
    
    @Test
    public void afterSteppingHeadMovesToCorrectDirection() {
        Position oldHead = this.game.getPositions().getFirst();
        this.game.step();
        Position head = this.game.getPositions().getFirst();
        
        switch (this.game.getDirection()) {
            case 0: // up
                assertTrue(head.equals(oldHead.add(0, -1)));
                break;
            case 1: // right
                assertTrue(head.equals(oldHead.add(1, 0)));
                break;
            case 2: // down
                assertTrue(head.equals(oldHead.add(0, 1)));
                break;
            case 3: // left
                assertTrue(head.equals(oldHead.add(-1, 0)));
                break;
        }
    }
    
    @Test
    public void canChangeDirection() {
        int newDirection = (this.game.getDirection() + 1) % 4;
        this.game.enqueueDirection(newDirection);
        this.game.step();
        
        assertEquals(newDirection, this.game.getDirection());
    }
    
    @Test
    public void afterEatingScoreGrows() {
        Position head = this.game.getPositions().getFirst();
        switch (this.game.getDirection()) {
            case 0: // up
                this.game.setFoodPosition(head.add(0, -1));
                break;
            case 1: // right
                this.game.setFoodPosition(head.add(1, 0));
                break;
            case 2: // down
                this.game.setFoodPosition(head.add(0, 1));
                break;
            case 3: // down
                this.game.setFoodPosition(head.add(-1, 0));
                break;
        }
        
        this.game.step();
        
        assertEquals(1, this.game.getScore());
    }
    
    @Test
    public void directionDoesNotChangeIfNotValid() {
        int oldDirection = this.game.getDirection();
        this.game.enqueueDirection(-1);
        this.game.enqueueDirection(4);
        
        this.game.step();
        assertEquals(oldDirection, this.game.getDirection());
        
        this.game.step();
        assertEquals(oldDirection, this.game.getDirection());
    }
    
    @Test
    public void cannotMake180Turn() {
        int oldDirection = this.game.getDirection();
        this.game.enqueueDirection((oldDirection + 2) % 4);
        this.game.step();
        
        assertEquals(oldDirection, this.game.getDirection());
    }
    
    @Test
    public void gameIsOverIfHeadBumpsToBody() {
        for (int i = 0; i < 3; ++i) {
            this.game.grow();
            this.game.step();
        }
        
        int direction = this.game.getDirection();
        for (int i = 0; i < 3; ++i) {
            this.game.enqueueDirection((direction + (i + 1)) % 4);
            this.game.step();
        }
        
        assertTrue(this.game.isGameOver());
    }
    
}
