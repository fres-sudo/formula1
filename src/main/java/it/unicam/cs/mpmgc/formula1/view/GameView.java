package it.unicam.cs.mpmgc.formula1.view;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView extends Pane {
    private final Map<Player, Circle> playerCircle = new HashMap<>();
    private final Map<Player, List<Line>> playerPath = new HashMap<>();
    private final List<Button> moveButtons = new ArrayList<>();;
    private final GameModel gameModel;

    private static final int TRACK_FACTOR = 2;
    private static final int BUTTON_SIZE = 10;

    public GameView(GameModel gameModel) {

        setPrefSize(800, 800);
        this.gameModel = gameModel;

        // Create and draw the grid canvas
        Canvas gridCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        drawGrid(gridCanvas);
        drawTrack(gameModel.getTrack());

        // Add grid canvas first so it stays in the background
        getChildren().add(gridCanvas);

        for(Player player : gameModel.getPlayers()) {
            //player.setPosition(gameModel.getTrack().getStartPoint());
            playerPath.put(player, new ArrayList<>());
            playerCircle.put(player, new Circle(5, Color.RED));
            getChildren().add(playerCircle.get(player));
        }
    }

    public void updatePlayerPosition(Player player, Point lastPosition) {
        //draw line
        Line line = drawLine(lastPosition, player.getPosition());
        playerPath.get(player).add(line);

        //move the circle
        moveCircle(player);
    }

    private Line drawLine(Point startPoint, Point endPoint) {
        Line line = new Line(
                startPoint.x(),
                startPoint.y(),
                endPoint.x(),
                endPoint.y()
        );
        line.setStroke(Color.RED);
        getChildren().add(line);
        return line;
    }

    private void moveCircle(Player player) {
        Point currentPosition = player.getPosition();

        Circle playerCircle = this.playerCircle.get(player);
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setToX(currentPosition.x());
        transition.setToY(currentPosition.y());
        transition.play();
    }

    public void drawMoveOptions(Point point, int[][] directions, EventHandler<ActionEvent> moveHandler) {
        clearMoveOptions();
        for (int[] direction : directions) {
            int newX = point.x() + (direction[0]);
            int newY = point.y() + (direction[1]);
            Point newPoint;
            Point startingPoint = new Point(gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR, gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
            if(point.equals(startingPoint)){
                newPoint = new Point(newX - point.x() + 5, newY - point.y() - 5);// +/- 5 to center the move button at the beginning of the race
            } else {
                newPoint = new Point(newX, newY);
            }
            Button moveButton = createMoveButton(newPoint, moveHandler);
            moveButtons.add(moveButton);
            getChildren().add(moveButton);
        }
    }

    private Button createMoveButton(Point point, EventHandler<ActionEvent> moveHandler) {
        Button moveButton = new Button();
        moveButton.getStyleClass().add("outline-button");
        moveButton.setLayoutX(point.x());
        moveButton.setLayoutY(point.y());
        moveButton.setShape(new Circle(5));
        moveButton.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        moveButton.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);
        moveButton.setOnAction(event -> moveHandler.handle(new ActionEvent(moveButton, null)));
        return moveButton;
    }

    public void clearMoveOptions() {
        for (Button button : moveButtons) {
            getChildren().remove(button);
        }
        moveButtons.clear();
    }

    private void drawTrack(Track track) {
        List<Point> points = track.getPoints();

        // Draw track lines
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            Line trackLine = new Line(
                    p1.x() * TRACK_FACTOR,
                    p1.y() * TRACK_FACTOR,
                    p2.x() * TRACK_FACTOR,
                    p2.y() * TRACK_FACTOR);
            trackLine.setStroke(Color.BLACK);
            trackLine.setStrokeWidth(2);
            getChildren().add(trackLine);
        }

        Point start = track.getStartPoint();
        Circle startCircle = new Circle(start.x() * TRACK_FACTOR, start.y() * TRACK_FACTOR + 10, 5, Color.GREEN);
        getChildren().add(startCircle);
    }

    private void drawGrid(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        int cellSize = 10;
        context.setStroke(Color.LIGHTGREY.deriveColor(0, 1.0, 1.0, 0.15)); //for the opacity

        for (int i = 0; i < canvas.getWidth(); i += cellSize) {
            context.strokeLine(i, 0, i, canvas.getHeight());
        }

        for (int i = 0; i < canvas.getHeight(); i += cellSize) {
            context.strokeLine(0, i, canvas.getWidth(), i);
        }
    }

    public void resetGame() {
        playerPath.clear();
    }
}
