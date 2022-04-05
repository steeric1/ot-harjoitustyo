package snake.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import snake.dao.CsvUserDao;
import snake.domain.SnakeService;

public class SnakeUi extends Application {
    
    static final int DIMENSION = 700;
    
    private SnakeService service;
    
    // Application views
    private View login;
    
    @Override
    public void init() {
        this.service = new SnakeService(new CsvUserDao());
    }
    
    @Override
    public void start(Stage stage) {
        this.login = new LoginView(this.service);
        
        stage.setScene(login.getScene());
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
