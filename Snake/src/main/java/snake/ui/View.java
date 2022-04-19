package snake.ui;

import javafx.scene.Scene;
import snake.domain.SnakeService;

public abstract class View {
    protected SnakeService service;
    protected ViewChanger viewChanger;
    private Scene scene;
    private boolean defer;
    
    public View(SnakeService service, ViewChanger changer, boolean defer) {
        this.service = service;
        this.viewChanger = changer;
        
        if (!defer)
            this.refresh();
    }
    
    public final void refresh() {
        this.scene = this.buildScene();
    }
    
    public final Scene getScene() {
        if (this.scene == null)
            this.refresh();
        
        return this.scene;
    }
    
    protected abstract Scene buildScene();
}
