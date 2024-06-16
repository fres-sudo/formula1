package it.unicam.cs.mpmgc.formula1.view;

import it.unicam.cs.mpmgc.formula1.model.GameState;
import it.unicam.cs.mpmgc.formula1.model.Observer;
import it.unicam.cs.mpmgc.formula1.model.Point;
import it.unicam.cs.mpmgc.formula1.model.Track;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import javafx.beans.Observable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView extends Pane implements Observer<GameState> {
    private Map<Player, Circle> playerViews = new HashMap<>();

    public GameView() {
        setPrefSize(800, 600); // Dimensioni predefinite della vista
        getStyleClass().add("game-view"); // Apply the CSS class
    }


    @Override
    public void update(GameState gameState) {
        drawTrack(gameState.getTrack());
        drawPlayers(gameState.getPlayers());
    }

    private void drawTrack(Track track) {
        List<Point> points = track.getPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            Line line = new Line(start.getX() * 20, start.getY() * 20, end.getX() * 20, end.getY() * 20);
            System.out.println("line: "+ line.getStartX() + " - " + line.getEndX() + " - " + line.getStartY() + " - " + line.getEndY());
            line.setStroke(Color.BLACK);
            getChildren().add(line);
        }
    }

    private void drawPlayers(List<Player> players) {
        for (Player player : players) {
            Circle playerView = playerViews.computeIfAbsent(player, p -> {
                Circle circle = new Circle(5, Color.RED);
                getChildren().add(circle);
                return circle;
            });

            Point position = player.getPosition();
            playerView.setCenterX(position.getX() * 20);
            playerView.setCenterY(position.getY() * 20);
        }
    }
}

