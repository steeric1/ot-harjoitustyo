package snake.ui;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import snake.domain.Score;
import snake.domain.SnakeService;
import snake.domain.User;

public class HiScoreView extends View {

    public HiScoreView(SnakeService service, ViewChanger changer) {
        super(service, changer, false);
    }
    
    @Override
    protected Scene buildScene() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        pane.setAlignment(Pos.TOP_LEFT);
        
        Label title = new Label("High Scores");
        title.setFont(new Font(20));
        pane.getChildren().add(title);
        
        List<Pair<User, Integer>> scores = super.service.getTopScores(10);
        if (scores.isEmpty()) {
            pane.getChildren().add(new Label("No high scores set yet!"));
        } else {
            for (int i = 0; i < scores.size(); ++i) {
                pane.getChildren().add(new Label((i + 1) + ". " + scores.get(i).getKey().getUsername() + ": " + scores.get(i).getValue()));
            }   
        }
        
        Button backToMenu = new Button("Back to menu");
        backToMenu.setOnAction(e -> {
            super.viewChanger.change(ViewType.MENU);
        });
        
        pane.getChildren().add(backToMenu);
        return new Scene(pane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
}
