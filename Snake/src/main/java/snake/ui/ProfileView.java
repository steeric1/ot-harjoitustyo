package snake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import snake.domain.SnakeService;
import snake.domain.UserOperationResult;

public class ProfileView extends View {
    
    public ProfileView(SnakeService service, ViewChanger changer) {
        super(service, changer, true);
    }

    @Override
    protected Scene buildScene() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        
        Label profileName = new Label("Profile: " + super.service.getLoggedInUser().getUsername());
        profileName.setFont(new Font(20));
        
        Button backToMenu = new Button("Back to menu");
        backToMenu.setOnAction(event -> {
            super.viewChanger.change(ViewType.MENU);
        });
        
        Label colorPrompt = new Label("Choose snake color:");
        Label colorChangeResult = new Label("");
        colorChangeResult.setVisible(false);
        
        ColorPicker colorPicker = new ColorPicker(super.service.getLoggedInUser().getColor());
        colorPicker.setOnAction(event -> {
            UserOperationResult result = super.service
                    .setUserColor(super.service.getLoggedInUser().getUsername(),
                            colorPicker.getValue());
            
            if (result != UserOperationResult.SUCCESS) {
                colorChangeResult.setText(result.toString());
                colorChangeResult.setTextFill(Color.RED);
                colorChangeResult.setVisible(true);
            } else {
                colorChangeResult.setText("Color updated!");
                colorChangeResult.setTextFill(Color.GREEN);
                colorChangeResult.setVisible(true);
            }
            
            super.service.login(super.service.getLoggedInUser().getUsername());
        });
        
        HBox colorChange = new HBox(10);
        colorChange.setPadding(new Insets(10));
        colorChange.getChildren().addAll(colorPrompt, colorPicker, colorChangeResult);
        
        Button changeUsername = new Button("Change username");
        changeUsername.setOnAction(event -> {
            Label newUsername = new Label("New username:");
            TextField newUsernameInput = new TextField();
           
            Label renameError = new Label();
            renameError.setTextFill(Color.RED);
            renameError.setVisible(false);
           
            HBox renamePane = new HBox(10);
            renamePane.setAlignment(Pos.TOP_LEFT);
            renamePane.getChildren().addAll(newUsername, newUsernameInput, renameError);
           
            Button finishRename = new Button("Rename");
            finishRename.setOnAction(ev -> {
                String username = newUsernameInput.getText();
                UserOperationResult result = super.service.renameUser(super.service.getLoggedInUser().getUsername(), username);
              
                if (result != UserOperationResult.SUCCESS) {
                    renameError.setText(result.toString());
                    renameError.setVisible(true);
                } else {
                    renameError.setText("");
                    renameError.setVisible(false);
                }
              
                super.service.login(username);
                super.viewChanger.change(ViewType.PROFILE); // Refresh stage.
           });
           
           pane.getChildren().removeAll(changeUsername, colorChange, backToMenu);
           pane.getChildren().addAll(renamePane, finishRename, colorChange, backToMenu);
        });
        
        pane.getChildren().addAll(profileName, changeUsername, colorChange, backToMenu);
        return new Scene(pane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
    
}
