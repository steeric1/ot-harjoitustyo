package snake.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import snake.domain.Game;
import snake.domain.Game.Position;
import snake.domain.Score;
import snake.domain.SnakeService;

public class GameView extends View {
    private static final int TARGET_CANVAS_SIZE = (int)(0.85 * SnakeUi.DIMENSION);
    private static final int GAME_SIZE = 14;
    private static final int TILE_SIZE = TARGET_CANVAS_SIZE / GAME_SIZE;
    private static final int HEAD_MARGIN = Math.round(TILE_SIZE / 8.f);
    private static final int BODY_MARGIN = Math.round(TILE_SIZE / 4.f);
    private static final int STEP_INTERVAL = 120;
    private static final int INITIAL_COOLDOWN = 1000;
    private static final int GAME_OVER_OVERLAY_FADE_TIME = 2000;
    private static final double GAME_OVER_OVERLAY_MAX_OPACITY = 0.80;
    
    private static int canvasSize = 0;
            
    public GameView(SnakeService service, ViewChanger changer) {
        super(service, changer, true);
    }
    
    @Override
    protected Scene buildScene() {
        StackPane pane = new StackPane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);
        
        canvasSize = TILE_SIZE * GAME_SIZE;
        Canvas canvas = new Canvas(canvasSize, canvasSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Game game = new Game(GAME_SIZE);
        renderFrame(game, gc); // Initial frame
        
        Rectangle borders = new Rectangle();
        borders.setStrokeWidth(4);
        borders.setStroke(Color.WHITE);
        borders.setFill(Color.TRANSPARENT);
        borders.setX((SnakeUi.DIMENSION - canvasSize) / 2 - 4);
        borders.setY((SnakeUi.DIMENSION - canvasSize) / 2 - 4);
        borders.setWidth(canvasSize + 8);
        borders.setHeight(canvasSize + 8);
        
        StackPane gameOverOverlay = new StackPane();
        gameOverOverlay.setAlignment(Pos.CENTER);
        
        Rectangle gameOverOverlayBackground = new Rectangle();
        gameOverOverlayBackground.setX(0.0);
        gameOverOverlayBackground.setY(0.0);
        gameOverOverlayBackground.setWidth(SnakeUi.DIMENSION);
        gameOverOverlayBackground.setHeight(SnakeUi.DIMENSION);
        gameOverOverlayBackground.setFill(Color.TRANSPARENT);
        gameOverOverlay.getChildren().add(gameOverOverlayBackground);

        HBox scoreContainer = new HBox(50);
        scoreContainer.setPadding(new Insets(10));
        scoreContainer.setAlignment(Pos.TOP_CENTER);
        
        Label score = new Label("Score: 0");
        score.setFont(new Font(24));
        score.setTextFill(Color.WHITE);

        Text t = new Text("Score: 000");
        t.setFont(score.getFont());
        score.setPrefWidth(t.getLayoutBounds().getWidth()); // Make sure label has a wide enough fixed width.
        
        List<Integer> userScores = super.service.getUserScores(super.service.getLoggedInUser().getUsername());
        int hiScoreValue = userScores.isEmpty() ? 0 : userScores.get(0);
        Label hiScore = new Label("High score: " + hiScoreValue);
        hiScore.setFont(new Font(24));
        hiScore.setTextFill(Color.WHITE);
        
        t.setText("High score: 000");
        t.setFont(hiScore.getFont());
        hiScore.setPrefWidth(t.getLayoutBounds().getWidth());
        
        scoreContainer.getChildren().addAll(score, hiScore);
        
        GameView view = this;
        new AnimationTimer() {
            private long previousStepTime = System.nanoTime();
            private int frames = 0;
            private double gameOverOverlayOpacity = 0.0;
            private VBox gameOverOverlayText;
            private boolean scoreSaved = false;
            
            @Override
            public void handle(long currentTime) {
                if (!game.isGameOver()) {
                    if ((this.frames == 0 && currentTime - this.previousStepTime < INITIAL_COOLDOWN * 1.e6)
                            || currentTime - this.previousStepTime < STEP_INTERVAL * 1.e6) {
                        return;
                    }
                    
                    this.frames++;
                    game.step();
                    score.setText("Score: " + game.getScore());
                    if (game.getScore() > hiScoreValue) {
                        hiScore.setText("High score: " + game.getScore());
                        hiScore.setTextFill(Color.GOLD);
                        hiScore.setFont(Font.font(hiScore.getFont().getName(), FontWeight.BOLD,
                                hiScore.getFont().getSize()));
                    }
                    view.renderFrame(game, gc);
                } else {
                    view.drawHead(game.getPositions().getFirst(), game.getPositions().get(1), gc, game.getDirection());
                    if (!this.scoreSaved) {
                        this.gameOverOverlayText = view.getGameOverOverlayText(game);
                        view.service.addScore(view.service.getLoggedInUser().getUsername(), game.getScore());
                        this.scoreSaved = true;
                    }
                    
                    double step = (currentTime - this.previousStepTime) / (GAME_OVER_OVERLAY_FADE_TIME * 1.e6);
                    step = Math.max(step, 1.0 / 256.0);
                    this.gameOverOverlayOpacity += step;
                    if (this.gameOverOverlayOpacity < GAME_OVER_OVERLAY_MAX_OPACITY) {
                        gameOverOverlayBackground.setFill(new Color(0.0, 0.0, 0.0, gameOverOverlayOpacity));
                    } else {
                        pane.getChildren().remove(scoreContainer);
                        gameOverOverlay.getChildren().add(this.gameOverOverlayText);
                        this.stop();
                    }
                }
                
                this.previousStepTime = currentTime;
            }
            
        }.start();
        
        pane.getChildren().addAll(canvas, borders, scoreContainer, gameOverOverlay);
        Scene scene = new Scene(pane, SnakeUi.DIMENSION, SnakeUi.DIMENSION);
        scene.setOnKeyPressed(ev -> {
            KeyCode code = ev.getCode();
            switch (code) {
                case UP:
                    game.enqueueDirection(0);
                    break;
                case RIGHT:
                    game.enqueueDirection(1);
                    break;
                case DOWN:
                    game.enqueueDirection(2);
                    break;
                case LEFT:
                    game.enqueueDirection(3);
                    break;
                default:
                    break;
            }
        });
        
        return scene;
    }
    
    private VBox getGameOverOverlayText(Game game) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        pane.setAlignment(Pos.CENTER);
        
        List<Integer> userScores = super.service.getUserScores(super.service.getLoggedInUser().getUsername());
        int hiScoreValue = userScores.isEmpty() ? 0 : userScores.get(0);
        
        Label score = new Label("Your score: " + game.getScore() + 
                (game.getScore() > hiScoreValue 
                        ? " (new high score! previous: " + hiScoreValue + ")" 
                        : " (high score: " + hiScoreValue + ")"));
        score.setTextFill(Color.WHITE);
        score.setFont(new Font(24));
        
        List<Score> allScores = super.service.getAllScores();
        int i = 0;
        for (ListIterator<Score> it = allScores.listIterator(); it.hasNext(); ++i) {
            Score next = it.next();
            if (next.getValue() < game.getScore()) {
                break;
            }
        }
        
        Label top = new Label("You set a top-" + (i + 1) + " score!");
        top.setTextFill(Color.WHITE);
        top.setFont(new Font(24));
        
        VBox prompts = new VBox(10);
        prompts.setPadding(new Insets(10));
        prompts.setAlignment(Pos.CENTER);
        
        Label newGame = new Label("Start new game");
        newGame.setTextFill(Color.GOLD);
        newGame.setFont(new Font(24));
        
        newGame.setOnMouseEntered(ev -> {
            newGame.setTextFill(Color.GOLD.darker());
            newGame.setFont(Font.font(newGame.getFont().getName(), FontWeight.BOLD,
                    newGame.getFont().getSize()));
        });
        
        newGame.setOnMouseExited(ev -> {
            newGame.setTextFill(Color.GOLD);
            newGame.setFont(Font.font(newGame.getFont().getName(), FontWeight.NORMAL,
                    newGame.getFont().getSize()));
        });
        
        newGame.setOnMouseClicked(ev -> {
            super.viewChanger.change(ViewType.GAME);
        });
        
        Label quit = new Label("Quit to menu");
        quit.setTextFill(Color.GOLD);
        quit.setFont(new Font(24));
        
        quit.setOnMouseEntered(ev -> {
            quit.setTextFill(Color.GOLD.darker());
            quit.setFont(Font.font(quit.getFont().getName(), FontWeight.BOLD,
                    quit.getFont().getSize()));
        });
        
        quit.setOnMouseExited(ev -> {
            quit.setTextFill(Color.GOLD);
            quit.setFont(Font.font(quit.getFont().getName(), FontWeight.NORMAL,
                    quit.getFont().getSize()));
        });
        
        quit.setOnMouseClicked(ev -> {
           super.viewChanger.change(ViewType.MENU); 
        });
        
        prompts.getChildren().addAll(newGame, quit);
        pane.getChildren().addAll(score, top, prompts);
        return pane;
    }
    
    private void renderFrame(Game game, GraphicsContext gc) {
        // Draw the background.
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasSize, canvasSize);
        this.drawGrid(gc);
                
        // Draw the food.
        Position food = game.getFoodPosition();
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(food.x * TILE_SIZE + BODY_MARGIN, food.y * TILE_SIZE + BODY_MARGIN,
                TILE_SIZE - BODY_MARGIN * 2, TILE_SIZE - BODY_MARGIN * 2);
        
        // Draw the snake.
        Color baseColor = super.service.getLoggedInUser().getColor();
        gc.setFill(baseColor);
        LinkedList<Position> positions = game.getPositions();
        ListIterator<Position> it = positions.listIterator();
        
        // Draw head larger than body.
        this.drawHead(it.next(), it.next(), gc, game.getDirection());
        it.previous();
        
        // Draw body.
        Position previous, current, next;
        int i = 0;
        while (it.hasNext()) {
            if (i++ % 2 == 0) {
                gc.setFill(baseColor.darker().darker());
            } else {
                gc.setFill(baseColor);
            }
            previous = it.previous(); it.next();
            current = it.next();
            next = null;
            if (it.hasNext()) {
                next = it.next(); it.previous();
            }
            
            gc.fillRect(current.x * TILE_SIZE + BODY_MARGIN, current.y * TILE_SIZE + BODY_MARGIN, 
                    TILE_SIZE - BODY_MARGIN * 2, TILE_SIZE - BODY_MARGIN * 2);
            
            this.completeBodyChunk(current, previous, gc);
            if (next != null) {
                this.completeBodyChunk(current, next, gc);
            }
        }
    }
    
    private void drawGrid(GraphicsContext gc) {
        gc.setFill(new Color(0.12, 0.12, 0.12, 1.0));
        for (int x = 0; x < GAME_SIZE; ++x) {
            for (int y = 0; y < GAME_SIZE; ++y) {
                gc.fillRect(x * TILE_SIZE + HEAD_MARGIN, y * TILE_SIZE + HEAD_MARGIN,
                        TILE_SIZE - HEAD_MARGIN * 2, TILE_SIZE - HEAD_MARGIN * 2);
            }
        }
    }
    
    private void drawHead(Position head, Position next, GraphicsContext gc, int direction) {
        gc.setFill(super.service.getLoggedInUser().getColor());
        gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN, head.y * TILE_SIZE + HEAD_MARGIN,
                TILE_SIZE - HEAD_MARGIN * 2, TILE_SIZE - HEAD_MARGIN * 2);
        
        this.drawEyes(head, gc, direction);
        
        if (next.equals(head.add(1, 0)) || next.equals(head.add(-(GAME_SIZE - 1), 0))) {
            gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN, head.y * TILE_SIZE + BODY_MARGIN,
                    HEAD_MARGIN, TILE_SIZE - BODY_MARGIN * 2);
        } else if (next.equals(head.add(-1, 0)) || next.equals(head.add(GAME_SIZE -1, 0))) {
            gc.fillRect(head.x * TILE_SIZE, head.y * TILE_SIZE + BODY_MARGIN,
                    HEAD_MARGIN, TILE_SIZE - BODY_MARGIN * 2);
        } else if (next.equals(head.add(0, 1)) || next.equals(head.add(0, -(GAME_SIZE - 1)))) {
            gc.fillRect(head.x * TILE_SIZE + BODY_MARGIN, (head.y + 1) * TILE_SIZE - HEAD_MARGIN,
                    TILE_SIZE - BODY_MARGIN * 2, HEAD_MARGIN);
        } else if (next.equals(head.add(0, -1)) || next.equals(head.add(0, GAME_SIZE - 1))) {
            gc.fillRect(head.x * TILE_SIZE + BODY_MARGIN, head.y * TILE_SIZE,
                    TILE_SIZE - BODY_MARGIN * 2, HEAD_MARGIN);
        }
    }
    
    private void drawEyes(Position head, GraphicsContext gc, int direction) {
        Paint col = gc.getFill();
        int eyeMargin = (TILE_SIZE - HEAD_MARGIN * 2) / 10;
        int eyeSize = (TILE_SIZE - HEAD_MARGIN * 2) / 3;
        int pupilSize = (int)(eyeSize / 1.5);
        switch (direction) {
            case 0:
                gc.setFill(Color.WHITE);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        eyeSize, eyeSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        eyeSize, eyeSize);
                
                gc.setFill(Color.BLACK);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        pupilSize, pupilSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        pupilSize, pupilSize);
                break;
            case 1:
                gc.setFill(Color.WHITE);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - eyeSize - 1,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        eyeSize, eyeSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - eyeSize - 1,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        eyeSize, eyeSize);
                
                gc.setFill(Color.BLACK);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - pupilSize - 1,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        pupilSize, pupilSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - pupilSize - 1,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        pupilSize, pupilSize);
                break;
            case 2:
                gc.setFill(Color.WHITE);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - eyeSize - 1,
                        eyeSize, eyeSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - eyeSize - 1,
                        eyeSize, eyeSize);
                
                gc.setFill(Color.BLACK);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin + eyeSize - pupilSize,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - pupilSize - 1,
                        pupilSize, pupilSize);
                gc.fillRect((head.x + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - pupilSize - 1,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin * 2 - pupilSize - 1,
                        pupilSize, pupilSize);
                break;
            case 3:
                gc.setFill(Color.WHITE);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin,
                        eyeSize, eyeSize);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - eyeSize - 1,
                        eyeSize, eyeSize);
                
                gc.setFill(Color.BLACK);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        head.y * TILE_SIZE + HEAD_MARGIN + eyeMargin + eyeSize - pupilSize,
                        pupilSize, pupilSize);
                gc.fillRect(head.x * TILE_SIZE + HEAD_MARGIN + eyeMargin * 2,
                        (head.y + 1) * TILE_SIZE - HEAD_MARGIN - eyeMargin - pupilSize - 1,
                        pupilSize, pupilSize);
                break;
        }
        
        gc.setFill(col);
    }
    
    private void completeBodyChunk(Position current, Position other, GraphicsContext gc) {
        if (other.equals(current.add(1, 0)) || other.equals(current.add(-(GAME_SIZE - 1), 0))) {
            gc.fillRect((current.x + 1) * TILE_SIZE - BODY_MARGIN, current.y * TILE_SIZE + BODY_MARGIN,
                    BODY_MARGIN, TILE_SIZE - BODY_MARGIN * 2);
        } else if (other.equals(current.add(-1, 0)) || other.equals(current.add(GAME_SIZE -1, 0))) {
            gc.fillRect(current.x * TILE_SIZE, current.y * TILE_SIZE + BODY_MARGIN,
                    BODY_MARGIN, TILE_SIZE - BODY_MARGIN * 2);
        } else if (other.equals(current.add(0, 1)) || other.equals(current.add(0, -(GAME_SIZE - 1)))) {
            gc.fillRect(current.x * TILE_SIZE + BODY_MARGIN, (current.y + 1) * TILE_SIZE - BODY_MARGIN,
                    TILE_SIZE - BODY_MARGIN * 2, BODY_MARGIN);
        } else if (other.equals(current.add(0, -1)) || other.equals(current.add(0, GAME_SIZE - 1))) {
            gc.fillRect(current.x * TILE_SIZE + BODY_MARGIN, current.y * TILE_SIZE,
                    TILE_SIZE - BODY_MARGIN * 2, BODY_MARGIN);
        }
    }
}