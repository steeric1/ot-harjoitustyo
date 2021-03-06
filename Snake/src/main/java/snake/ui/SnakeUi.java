package snake.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import snake.dao.CsvScoreDao;
import snake.dao.CsvUserDao;
import snake.domain.SnakeService;

public class SnakeUi extends Application {
    
    static final int DIMENSION = 700;
    
    private SnakeService service;
    private Stage stage;
    
    // Application views
    private View login;
    private View menu;
    private View profile;
    private View hiScore;
    private View game;
    
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
                case HI_SCORE:
                    this.setActiveView(this.hiScore);
                    break;
                case GAME:
                    this.setActiveView(this.game);
                    break;
                default:
                    break;
            }
        };
        
        this.service = new SnakeService(new CsvUserDao("users.csv"), new CsvScoreDao("scores.csv"));
        this.login = new LoginView(this.service, changer);
        this.menu = new MenuView(this.service, changer);
        this.profile = new ProfileView(this.service, changer);
        this.hiScore = new HiScoreView(this.service, changer);
        this.game = new GameView(this.service, changer);
    }
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
        
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
