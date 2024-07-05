package it.unicam.cs.oop.formulauno.controller;

import it.unicam.cs.oop.formulauno.model.GameModel;
import it.unicam.cs.oop.formulauno.model.player.HumanPlayer;
import it.unicam.cs.oop.formulauno.model.player.Player;
import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.view.GameView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.function.Consumer;

import static it.unicam.cs.oop.formulauno.model.point.PointJTO.AXIS_FACTOR;
import static it.unicam.cs.oop.formulauno.model.track.Track.TRACK_FACTOR;

/**
 * Describes the movement of a Human Player
 */
public class HumanMoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final Consumer<GameState> onMoveCallback;
    private final Consumer<GameState> resetGameCallback;
    private final BotMoveController botMoveController;
    private Point gap; //variable to keep track of the gap between last position and new position and draw the move option accordingly

    /**
     * Constructor method that manages storing the last position and pass variables from higher level.
     *
     * @param gameModel         instance of {@link GameModel}
     * @param gameView          instance of {@link GameView}
     * @param onMoveCallback    Lambda function to notify and update the {@link GameState} and {@link TimerController}
     * @param resetGameCallback Lambda function to notify the reset of the game.
     * @param botMoveController instance of BotMoveController since it strictly depends on HumanPlayer movement
     */
    public HumanMoveController(GameModel gameModel, GameView gameView, Consumer<GameState> onMoveCallback, BotMoveController botMoveController, Consumer<GameState> resetGameCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.onMoveCallback = onMoveCallback;
        this.resetGameCallback = resetGameCallback;
        //the gap at beginning will be 0 for understandable reasons
        this.gap = new Point(
                gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR,
                gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR); // for track scale adaptation
        this.botMoveController = botMoveController;
    }

    /**
     * Method responsible to notify the View {@link it.unicam.cs.oop.formulauno.view}
     * about player movement for correctly UI update and for draw the new move options
     * to make the player able to perform the next move.
     *
     * @param humanPlayer  the HumanPlayer the user updates
     * @param lastPosition the last known position of the human player
     */
    public void updatePlayerPosition(HumanPlayer humanPlayer, Point lastPosition) {
        gameView.updatePlayerPosition(humanPlayer, lastPosition);
        findMoveOptions(humanPlayer.getPosition());
    }

    /**
     * Method responsible to compute the directions of the new move options
     * and to notify the UI with the newest directions and position to place
     * the new Move Buttons.
     *
     * @param point the point where human player is moved
     */
    public void findMoveOptions(Point point) {

        int x = gap.x();
        int y = gap.y();
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
        HumanPlayer humanPlayer = getHumanPlayer();
        Button moveButton = (Button) event.getSource();
        Point newPoint = new Point((int) moveButton.getLayoutX(), (int) moveButton.getLayoutY()); //the point the player decide to move
        if (gameModel.getTrack().isValidPosition(newPoint)) {
            if (gameModel.getTrack().isRaceEnded(newPoint)) {
                resetHumanPlayer();
                resetGameCallback.accept(GameState.WIN); //Can't put in "resetHumanPLayer" method to avoid infinite call stack
                return;
            }
            gap = new Point(newPoint.x() - humanPlayer.getPosition().x(), newPoint.y() - humanPlayer.getPosition().y());
            Point lastPosition = humanPlayer.getPosition(); // store the current position of the player before updating it
            gameModel.setPlayerPosition(humanPlayer, newPoint); //set the new position of the player
            updatePlayerPosition(humanPlayer, lastPosition); //update the UI
            onMoveCallback.accept(GameState.PROGRESS); //update the state
            botMoveController.updateBotPosition(); //update the bots positions
        } else {
            resetGameCallback.accept(GameState.CRASH);
        }
    }

    /**
     * Helper method to retrieve the current Human Player
     *
     * @return the current Human Player
     */
    private HumanPlayer getHumanPlayer() {
        for(Player player : gameModel.getPlayers()) {
            if(player instanceof HumanPlayer humanPlayer) {
                return humanPlayer;
            }
        }
        return new HumanPlayer(new Point(0,0)); //edge case, will never happen
    }

    /**
     * Method to reset the human player last known position, and notify the move controller about the restart.
     */
    public void resetHumanPlayer() {
        gap = new Point(gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR, gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
        findMoveOptions(gap);
    }
}
