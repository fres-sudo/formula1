package it.unicam.cs.mpmgc.formula1.view;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.view.drawer.PlayerDrawer;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.view.drawer.Drawer;
import it.unicam.cs.mpmgc.formula1.view.drawer.GridDrawer;
import it.unicam.cs.mpmgc.formula1.view.drawer.TrackDrawer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.mpmgc.formula1.model.track.Track.TRACK_FACTOR;

/**
 * Core class that defines the View of a game, extends a Pane
 * since it's a {@link Pane} UI object
 */
public class GameView extends Pane {

    private static final int BUTTON_SIZE = 10;

    private final List<Button> moveButtons = new ArrayList<>();
    private final GameModel gameModel;
    private final PlayerDrawer playerDrawer;
    private final Drawer<Canvas> gridDrawer;
    private final Drawer<Track> trackDrawer;

    /**
     * Constructor method that initialize all the Drawing parts of the UI
     *
     * @param gameModel instance of {@link GameModel}
     */
    public GameView(GameModel gameModel) {
        setPrefSize(800, 800);
        this.gameModel = gameModel;
        this.playerDrawer = new PlayerDrawer(gameModel.getPlayers());
        this.gridDrawer = new GridDrawer();
        this.trackDrawer = new TrackDrawer();

        // Create and draw the grid canvas
        Canvas gridCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        gridDrawer.draw(gridCanvas, this);

        trackDrawer.draw(gameModel.getTrack(), this);
        playerDrawer.initializePlayersView(this);
    }

    /**
     * Method to call the re-draw on the update of the player position
     *
     * @param player       the player the user want to update the position
     * @param lastPosition the last known position of the player
     */
    public void updatePlayerPosition(Player player, Point lastPosition) {
        playerDrawer.updateViewPlayerPosition(player, lastPosition, this);
    }

    /**
     * Method to draw in the UI all the 9 buttons responsible for the movement
     * of a human player
     *
     * @param point       the point where the buttons are centered
     * @param directions  all the 9 directions of the buttons
     * @param moveHandler the handler of the movement
     */
    public void drawMoveOptions(Point point, int[][] directions, EventHandler<ActionEvent> moveHandler) {
        clearMoveOptions();
        for (int[] direction : directions) {
            int newX = point.x() + direction[0];
            int newY = point.y() + direction[1];
            Point newPoint = new Point(newX, newY);
            Point startingPoint = new Point(gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR, gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
            if (point.equals(startingPoint)) {
                newPoint = new Point(newX - point.x() + 5, newY - point.y() - 5);// +/- 5 to center the move button at the beginning of the race
            }
            Button moveButton = createMoveButton(newPoint, moveHandler);
            moveButtons.add(moveButton);
            getChildren().add(moveButton);
        }
    }

    /**
     * Helper method to create and place the UI Button movement.
     *
     * @param point       the point where place the button
     * @param moveHandler the handler of the movement
     * @return an instance of a {@link Button}
     */
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

    /**
     * Helper method to clear the old move buttons.
     */
    private void clearMoveOptions() {
        for (Button button : moveButtons) {
            getChildren().remove(button);
        }
        moveButtons.clear();
    }

    /**
     * Method to redraw the players on reset.
     */
    public void resetGame() {
        getChildren().clear();
        playerDrawer.resetViewPlayers();
        Canvas gridCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        gridDrawer.draw(gridCanvas, this);
        playerDrawer.initializePlayersView(this);
        trackDrawer.draw(gameModel.getTrack(), this);
    }
}

