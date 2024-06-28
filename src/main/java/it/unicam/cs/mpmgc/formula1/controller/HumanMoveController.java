package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.view.GameView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.function.Consumer;

/**
 *  Describes the movement of a Human Player
 */
public class HumanMoveController {

    private static final int TRACK_FACTOR = 2;

    private final GameModel gameModel;
    private final GameView gameView;
    private final Consumer<Void> onMoveCallback;
    private final BotMoveController botMoveController;
    private Point lastPosition;

    /**
     * Constructor method that manages storing the last position and pass variables from higher level.
     *
     * @param gameModel instance of {@link GameModel}
     * @param gameView instance of {@link GameView}
     * @param onMoveCallback Lambda function to notify and update the {@link TimerController}
     * @param botMoveController instance of BotMoveController since it strictly depends on HumanPlayer movement
     */
    public HumanMoveController(GameModel gameModel, GameView gameView, Consumer<Void> onMoveCallback, BotMoveController botMoveController) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.onMoveCallback = onMoveCallback;
        this.lastPosition = new Point(
                gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR,
                gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR); // for track scale adaptation
        this.botMoveController = botMoveController;
    }

    /**
     * Method responsible to notify the View {@link it.unicam.cs.mpmgc.formula1.view}
     * about player movement for correctly UI update and for draw the new move options
     * to make the player able to perform the next move.
     *
     * @param humanPlayer the HumanPlayer the user updates
     * @param lastPosition the last known position of the human player
     */
    public void updatePlayerPosition(HumanPlayer humanPlayer, Point lastPosition) {
        gameView.updatePlayerPosition(humanPlayer, lastPosition);
        drawMoveOptions(humanPlayer.getPosition());
    }

    /**
     * Method responsible to compute the directions of the new move options
     * and to notify the UI with the newest directions and position to place
     * the new Move Buttons.
     *
     * @param point the point where human player is moved
     */
    public void drawMoveOptions(Point point) {
        int x = lastPosition.x();
        int y = lastPosition.y();
        int[][] directions = {
                {x, y}, {x - 10, y}, {x + 10, y},
                {x, y - 10}, {x, y + 10},
                {x - 10, y - 10}, {x - 10, y + 10},
                {x + 10, y - 10}, {x + 10, y + 10}
        };
        gameView.drawMoveOptions(point, directions, this::onMoveButtonClick);
    }

    /**
     * Method responsible to compute the new and last point and update accordingly the {@link GameModel}
     *
     * @param event to retrieve the button where the movement is started
     */
    private void onMoveButtonClick(ActionEvent event) {
        HumanPlayer humanPlayer = (HumanPlayer) gameModel.getPlayers().getFirst();
        Button moveButton = (Button) event.getSource();
        Point newPoint = new Point((int) moveButton.getLayoutX(), (int) moveButton.getLayoutY());

        if (gameModel.getTrack().isValidPosition(newPoint)) {
            int lastX = newPoint.x() - humanPlayer.getPosition().x();
            int lastY = newPoint.y() - humanPlayer.getPosition().y();
            Point tmp = new Point(humanPlayer.getPosition().x(), humanPlayer.getPosition().y()); // store the last known position
            lastPosition = new Point(lastX, lastY); //TODO UNDERSTAND THIS
            gameModel.setPlayerPosition(humanPlayer, newPoint);
            updatePlayerPosition(humanPlayer, tmp);
            if (onMoveCallback != null) {
                onMoveCallback.accept(null);
            }
            botMoveController.updateBotPosition();
        }
    }
}
