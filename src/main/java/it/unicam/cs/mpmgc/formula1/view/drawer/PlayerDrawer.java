package it.unicam.cs.mpmgc.formula1.view.drawer;

import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDrawer {
    private final Map<Player, Circle> playerCircle = new HashMap<>();
    private final Map<Player, List<Point[]>> playerPaths = new HashMap<>();
    private final LineDrawer lineDrawer;

    public PlayerDrawer(List<Player> players) {
        this.lineDrawer = new LineDrawer(Color.RED); // You can also make this configurable
        for (Player player : players) {
            playerPaths.put(player, new ArrayList<>());
            playerCircle.put(player, new Circle(5, player instanceof HumanPlayer ? Color.RED : Color.GREEN));
        }
    }

    public void initializePlayersView(Pane pane) {
        for (Circle circle : playerCircle.values()) {
            pane.getChildren().add(circle);
        }
    }

    public void updateViewPlayerPosition(Player player, Point lastPosition, Pane pane) {
        Point currentPosition = player.getPosition();
        Point[] linePoints = {lastPosition, currentPosition};
        playerPaths.get(player).add(linePoints);
        lineDrawer.draw(linePoints, pane);
        moveCircle(player);
    }

    private void moveCircle(Player player) {
        Point currentPosition = player.getPosition();
        Circle playerCircle = this.playerCircle.get(player);
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setToX(currentPosition.x());
        transition.setToY(currentPosition.y());
        transition.play();
    }

    public void resetViewPlayers() {
        playerPaths.clear();
    }
}
