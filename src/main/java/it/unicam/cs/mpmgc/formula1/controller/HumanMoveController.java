package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.view.GameView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.function.Consumer;

import static it.unicam.cs.mpmgc.formula1.model.point.PointJTO.AXIS_FACTOR;
import static it.unicam.cs.mpmgc.formula1.model.track.Track.TRACK_FACTOR;

/**
 * Describes the movement of a Human Player
 */
public class HumanMoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final Consumer<GameState> onMoveCallback;
    private final Consumer<GameState> resetGameCallback;
    private final BotMoveController botMoveController;
    private Point lastPosition;

    /**
     * Constructor method that manages storing the last position and pass variables from higher level.
     *
     * @param gameModel         instance of {@link GameModel}
     * @param gameView          instance of {@link GameView}
     * @param onMoveCallback    Lambda function to notify and update the {@link TimerController}
     * @param botMoveController instance of BotMoveController since it strictly depends on HumanPlayer movement
     */
    public HumanMoveController(GameModel gameModel, GameView gameView, Consumer<GameState> onMoveCallback, BotMoveController botMoveController, Consumer<GameState> resetGameCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.onMoveCallback = onMoveCallback;
        this.resetGameCallback = resetGameCallback;
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
     * @param humanPlayer  the HumanPlayer the user updates
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
                {x, y}, {x - AXIS_FACTOR, y}, {x + AXIS_FACTOR, y},
                {x, y - AXIS_FACTOR}, {x, y + AXIS_FACTOR},
                {x - AXIS_FACTOR, y - AXIS_FACTOR}, {x - AXIS_FACTOR, y + AXIS_FACTOR},
                {x + AXIS_FACTOR, y - AXIS_FACTOR}, {x + AXIS_FACTOR, y + AXIS_FACTOR}
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
            if (gameModel.getTrack().isRaceEnded(newPoint)) {
                resetHumanPlayer();
                resetGameCallback.accept(GameState.WIN); //Can't put in resetHumanPLayer method to avoid infinite call stack
                return;
            }
            int lastX = newPoint.x() - humanPlayer.getPosition().x();
            int lastY = newPoint.y() - humanPlayer.getPosition().y();
            Point tmp = new Point(humanPlayer.getPosition().x(), humanPlayer.getPosition().y()); // store the last known position
            lastPosition = new Point(lastX, lastY); //TODO UNDERSTAND THIS
            gameModel.setPlayerPosition(humanPlayer, newPoint);
            updatePlayerPosition(humanPlayer, tmp);
            onMoveCallback.accept(GameState.PROGRESS);
            botMoveController.updateBotPosition();
        } else {
            resetGameCallback.accept(GameState.CRASH);
        }
    }

    /**
     * Method to reset the human player last known position, and notify the move controller about the restart.
     */
    public void resetHumanPlayer() {
        lastPosition = new Point(gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR, gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
        drawMoveOptions(lastPosition);
    }
}
