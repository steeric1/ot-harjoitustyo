package snake.ui;

import javafx.scene.Scene;
import snake.domain.SnakeService;

public abstract class View {
    protected SnakeService service;
    protected ViewChanger viewChanger;
    
    public View(SnakeService service, ViewChanger changer) {
        this.service = service;
        this.viewChanger = changer;
    }
    
    public abstract Scene getScene();
}
