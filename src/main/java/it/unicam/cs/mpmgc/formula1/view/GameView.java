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

public class GameView extends Pane {
    private static final int TRACK_FACTOR = 2;
    private static final int BUTTON_SIZE = 10;

    private final List<Button> moveButtons = new ArrayList<>();
    private final GameModel gameModel;
    private final PlayerDrawer playerManager;
    private final Drawer<Canvas> gridDrawer;
    private final Drawer<Track> trackDrawer;

    public GameView(GameModel gameModel) {
        setPrefSize(800, 800);
        this.gameModel = gameModel;
        this.playerManager = new PlayerDrawer(gameModel.getPlayers());
        this.gridDrawer = new GridDrawer();
        this.trackDrawer = new TrackDrawer();

        // Create and draw the grid canvas
        Canvas gridCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        gridDrawer.draw(gridCanvas, this);

        trackDrawer.draw(gameModel.getTrack(), this);

        playerManager.initializePlayersView(this);
    }

    public void updatePlayerPosition(Player player, Point lastPosition) {
        playerManager.updateViewPlayerPosition(player, lastPosition, this);
    }

    public void drawMoveOptions(Point point, int[][] directions, EventHandler<ActionEvent> moveHandler) {
        clearMoveOptions();
        for (int[] direction : directions) {
            int newX = point.x() + direction[0];
            int newY = point.y() + direction[1];
            Point newPoint = new Point(newX, newY);
            Point startingPoint = new Point(gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR, gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
            if(point.equals(startingPoint)){
                newPoint = new Point(newX - point.x() + 5, newY - point.y() - 5);// +/- 5 to center the move button at the beginning of the race
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

    public void resetGame() {
        playerManager.resetViewPlayers();
    }
}
