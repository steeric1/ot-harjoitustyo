package snake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import snake.domain.SnakeService;
import snake.domain.UserCreationResult;

public class LoginView extends View {
    private Scene scene;
    
    public LoginView(SnakeService service) {
        super(service);
        
        this.scene = this.createScene();
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }
    
    private Scene createScene() {
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(10));
        loginPane.setAlignment(Pos.TOP_CENTER);
        
        // Elements relating to creation of new users
        Button createNewUser = new Button("Create new user");
        createNewUser.setOnAction(event -> {
            Label newUsername = new Label("Enter username:");
            TextField newUsernameInput = new TextField();
            
            Label userCreationError = new Label();
            userCreationError.setTextFill(Color.RED);
            userCreationError.setVisible(false);
            
            HBox userCreationPane = new HBox(10);
            userCreationPane.setAlignment(Pos.CENTER);
            userCreationPane.getChildren().addAll(newUsername, newUsernameInput,
                    userCreationError);
            
            Button finishUserCreation = new Button("Create user");
            finishUserCreation.setOnAction(ev -> {
                UserCreationResult result = super.service
                        .createUser(newUsernameInput.getText());
                
                if (result != UserCreationResult.SUCCESS) {
                    userCreationError.setText(result.toString());
                    userCreationError.setVisible(true);
                } else {
                    userCreationError.setVisible(false);
                }
                
                // TODO: Login to the newly created user.
            });
            
            loginPane.getChildren().remove(createNewUser);
            loginPane.getChildren().addAll(userCreationPane, finishUserCreation);
        });
                
        loginPane.getChildren().add(createNewUser);
        return new Scene(loginPane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
}
