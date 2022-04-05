package snake.ui;

import javafx.scene.Scene;
import snake.domain.SnakeService;

public abstract class View {
    protected SnakeService service;
    
    public View(SnakeService service) {
        this.service = service;
    }
    
    public abstract Scene getScene();
}
