package it.unicam.cs.oop.formulauno.view.drawer;

import it.unicam.cs.oop.formulauno.model.player.HumanPlayer;
import it.unicam.cs.oop.formulauno.model.player.Player;
import it.unicam.cs.oop.formulauno.model.point.Point;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to perform the drawing in the UI of the players
 */
public class PlayerDrawer {

    private static final Color playerColor =  Color.ORANGERED;
    private static final Color botColor =  Color.DARKSEAGREEN.darker().desaturate();

    private final Map<Player, Circle> playerCircle = new HashMap<>();
    private final Map<Player, List<Point>> playerPaths = new HashMap<>();
    private final Map<Player, LineDrawer> playerLine = new HashMap<>();

    /**
     * Constructor method to define and initialize the paths and the circles and their colors.
     *
     * @param players the list of the players that are currently playing the game
     */
    public PlayerDrawer(List<Player> players) {
        for (Player player : players) {
            playerPaths.put(player, new ArrayList<>());
            playerLine.put(player, new LineDrawer(player instanceof HumanPlayer ? playerColor : botColor));
            playerCircle.put(player, new Circle(5, player instanceof HumanPlayer ? playerColor : botColor));
        }
    }

    /**
     * Method to draw put on the UI the circles of the players
     *
     * @param pane the pane where to place the circles of the players
     */
    public void initializePlayersView(Pane pane) {
        for (Circle circle : playerCircle.values()) {
            pane.getChildren().add(circle);
        }
    }

    /**
     * Method to re-draw the Path and the Circle according to the updated player position.
     *
     * @param player       the player the user want to update the position
     * @param lastPosition the last known position of the player
     * @param pane         the pane where the player is placed
     */
    public void updateViewPlayerPosition(Player player, Point lastPosition, Pane pane) {
        Point currentPosition = player.getPosition();
        Point[] linePoints = {lastPosition, currentPosition};
        playerPaths.get(player).add(currentPosition);
        playerLine.get(player).draw(linePoints, pane);
        moveCircle(player);
    }

    /**
     * Helper method to re-draw the UI according to the updated player position.
     *
     * @param player the player which the corresponding circle has to be updated
     */
    private void moveCircle(Player player) {
        Point currentPosition = player.getPosition();
        Circle playerCircle = this.playerCircle.get(player);
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), playerCircle);
        transition.setToX(currentPosition.x());
        transition.setToY(currentPosition.y());
        transition.play();
    }

    /**
     * Method to reset the paths of the players, used in the GameView
     */
    public void resetViewPlayers() {
        for (Player player : playerPaths.keySet()) {
            playerPaths.get(player).clear();
        }
    }
}
