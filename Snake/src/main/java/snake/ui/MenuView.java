package snake.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import snake.domain.SnakeService;

public class MenuView extends View {
    
    public MenuView(SnakeService service, ViewChanger changer) {
        super(service, changer);
    }
    
    @Override
    public Scene getScene() {
        VBox pane = new VBox(10);
        Label hello = new Label("Hello " + super.service.getLoggedInUser().getUsername() + "\n\nTODO");
        pane.getChildren().add(hello);
    
        return new Scene(pane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
    
}
