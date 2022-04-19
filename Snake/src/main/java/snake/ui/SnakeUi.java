package snake.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import snake.dao.CsvUserDao;
import snake.domain.SnakeService;

public class SnakeUi extends Application {
    
    static final int DIMENSION = 500;
    
    private SnakeService service;
    private Stage stage;
    
    // Application views
    private View login;
    private View menu;
    private View profile;
    
    @Override
    public void init() {
        ViewChanger changer = type -> {
            switch (type) {
                case LOGIN:
                    this.setActiveView(this.login);
                    break;
                case MENU:
                    this.setActiveView(this.menu);
                    break;
                case PROFILE:
                    this.setActiveView(this.profile);
                    break;
                default:
                    break; // TODO: add rest
            }
        };
        
        this.service = new SnakeService(new CsvUserDao("users.csv"));
        this.login = new LoginView(this.service, changer);
        this.menu = new MenuView(this.service, changer);
        this.profile = new ProfileView(this.service, changer);
    }
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        
        this.stage.setScene(login.getScene());
        this.stage.show();
    }
    
    public void setActiveView(View view) {
        view.refresh();
        this.stage.setScene(view.getScene());
    }
    
    public static void main(String[] args) {
        launch();
    }
}
