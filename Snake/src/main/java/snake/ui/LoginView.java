package snake.ui;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import snake.domain.SnakeService;
import snake.domain.UserOperationResult;

public class LoginView extends View {

    public LoginView(SnakeService service, ViewChanger changer) {
        super(service, changer, false);
    }
    
    @Override
    protected Scene buildScene() {
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(10));
        loginPane.setAlignment(Pos.TOP_LEFT);
        
        // List pre-existing users
        List<String> usernames = super.service.getAllUsernames();
        if (!usernames.isEmpty()) {
            Label login = new Label("Login:");
            login.setFont(new Font(20));
            loginPane.getChildren().add(login);

            for (String name : usernames) {
                Button loginBtn = new Button(name);
                loginBtn.setOnAction(event -> {
                    super.service.login(name);
                    super.viewChanger.change(ViewType.MENU);
                });

                loginPane.getChildren().add(loginBtn);
            }
        }
        
        // Elements relating to creation of new users
        Label newUser = new Label("First time?");
        newUser.setFont(new Font(20));
        Button createNewUser = new Button("Create new user");
        createNewUser.setOnAction(event -> {
            Label newUsername = new Label("Enter username:");
            TextField newUsernameInput = new TextField();
            
            Label userCreationError = new Label();
            userCreationError.setTextFill(Color.RED);
            userCreationError.setVisible(false);
            
            HBox userCreationPane = new HBox(10);
            userCreationPane.setAlignment(Pos.TOP_LEFT);
            userCreationPane.getChildren().addAll(newUsername, newUsernameInput,
                    userCreationError);
            
            Button finishUserCreation = new Button("Create user");
            finishUserCreation.setOnAction(ev -> {
                String username = newUsernameInput.getText();
                UserOperationResult result = super.service
                        .createUser(username);
                
                if (result != UserOperationResult.SUCCESS) {
                    userCreationError.setText(result.toString());
                    userCreationError.setVisible(true);
                } else {
                    userCreationError.setText("");
                    userCreationError.setVisible(false);
                }
                
                super.service.login(username);
                super.viewChanger.change(ViewType.MENU);
                newUsernameInput.setText("");
            });
            
            loginPane.getChildren().remove(createNewUser);
            loginPane.getChildren().addAll(userCreationPane, finishUserCreation);
        });
                
        loginPane.getChildren().addAll(newUser, createNewUser);
        return new Scene(loginPane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
    }
}
