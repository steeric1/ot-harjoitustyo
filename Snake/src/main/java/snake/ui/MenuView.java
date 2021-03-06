package snake.ui;

import java.util.List;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import snake.domain.SnakeService;

public class MenuView extends View {
    
    public MenuView(SnakeService service, ViewChanger changer) {
        super(service, changer, true);
    }
    
    @Override
    protected Scene buildScene() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        Label hello = new Label("Hello, " + super.service.getLoggedInUser().getUsername() + "!");
        hello.setFont(new Font(20));
        
        List<Integer> userScores = super.service.getUserScores(super.service.getLoggedInUser().getUsername());
        String hiScoreText = userScores.isEmpty() ? "(no high score)" : Integer.toString(userScores.get(0));
        
        Label hiScore = new Label("Personal high score: " + hiScoreText);
        hiScore.setFont(new Font(16));
        
        Button openProfile = new Button("Open profile settings");
        openProfile.setOnAction(event -> {
            super.viewChanger.change(ViewType.PROFILE);
        });
        
        Button viewHiScores = new Button("View high scores");
        viewHiScores.setOnAction(event -> {
            super.viewChanger.change(ViewType.HI_SCORE);
        });
        
        Button startGame = new Button("Start a new game");
        startGame.setOnAction(event -> {
            super.viewChanger.change(ViewType.GAME);
        });
        
        Button logoff = new Button("Log off");
        logoff.setOnAction(event -> {
            super.service.logoff();
            super.viewChanger.change(ViewType.LOGIN);
        });
        
        pane.getChildren().addAll(hello, hiScore, openProfile, viewHiScores, startGame, logoff);
        return new Scene(pane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
}
