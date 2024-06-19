package it.unicam.cs.mpmgc.formula1.view;

import it.unicam.cs.mpmgc.formula1.model.Track;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.parser.TrackParser;
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
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView extends Pane {
    private final Circle playerCircle;
    private final List<Button> moveButtons = new ArrayList<>();
    private final List<Line> playerPath = new ArrayList<>();
    private int currentX = 0;
    private int currentY = 0;

    private final int LINE_FACTOR = 10;
    private final int TRACK_FACTOR = 20;
    private final int BUTTON_SIZE = 20;

    public GameView() {
        setPrefSize(800, 800);

        // Create and draw the grid canvas
        Canvas gridCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        drawGrid(gridCanvas);

        // Add grid canvas first so it stays in the background
        getChildren().add(gridCanvas);

        // Initialize player circle
        playerCircle = new Circle(5, Color.RED);
        loadTrack();
        getChildren().add(playerCircle);
    }

    private void drawGrid(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int cellSize = 10;
        gc.setStroke(Color.LIGHTGREY.deriveColor(0, 1.0, 1.0, 0.15)); //for the opacity


        for (int i = 0; i < canvas.getWidth(); i += cellSize) {
            gc.strokeLine(i, 0, i, canvas.getHeight());
        }

        for (int i = 0; i < canvas.getHeight(); i += cellSize) {
            gc.strokeLine(0, i, canvas.getWidth(), i);
        }
    }

    public void updatePlayerPosition(int x, int y) {

        Line pathLine = new Line(currentX * LINE_FACTOR, currentY * LINE_FACTOR, x * LINE_FACTOR, y * LINE_FACTOR);
        pathLine.setStroke(Color.RED);
        playerPath.add(pathLine);
        getChildren().add(pathLine);

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setToX(x * LINE_FACTOR);
        transition.setToY(y * LINE_FACTOR);
        transition.setOnFinished(event -> {
            currentX = x;
            currentY = y;
        });
        transition.play();
    }


    public void drawMoveOptions(int x, int y, int[][] directions, EventHandler<ActionEvent> moveHandler) {
        clearMoveOptions();
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            Button moveButton = createMoveButton(newX, newY, moveHandler);
            moveButtons.add(moveButton);
            getChildren().add(moveButton);
        }
    }

    private Button createMoveButton(int x, int y, EventHandler<ActionEvent> moveHandler) {
        Button moveButton = new Button();
        moveButton.getStyleClass().add("outline-button");
        moveButton.setLayoutX(x * LINE_FACTOR);
        moveButton.setLayoutY(y * LINE_FACTOR);
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

    private void loadTrack() {
        PointMapper pointMapper = new PointMapper();
        TrackParser parser = new TrackParser(pointMapper);
        try {
            Track track = parser.parse("src/main/resources/track.json");
            drawTrack(track);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        Circle startCircle = new Circle(start.x() * LINE_FACTOR, start.y() * LINE_FACTOR, 5, Color.GREEN);
        getChildren().add(startCircle);
    }
}
