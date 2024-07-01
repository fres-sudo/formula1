package it.unicam.cs.mpmgc.formula1.controller;

import com.sun.jdi.VoidValue;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;
import javafx.scene.control.Label;


import static it.unicam.cs.mpmgc.formula1.model.track.Track.TRACK_FACTOR;

/**
 * Main Class to handle movements
 *
 */
public class MoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final HumanMoveController humanMoveController;
    private final BotMoveController botMoveController;
    private final TimerController timerController;
    private final Label stateLabel;

    /**
     * The Constructor of the Move Controller manages both model, view and other sub-movement controllers
     *
     * @param gameModel instance of {@link GameModel}
     * @param gameView instance of {@link GameView}
     * @param timerController instance of {@link TimerController}
     */
    public MoveController(GameModel gameModel, GameView gameView, TimerController timerController, Label stateLabel) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.timerController = timerController;
        this.stateLabel = stateLabel;
        this.botMoveController = new BotMoveController(
                gameModel,
                gameView,
                timerController,
                this::resetGame);
        this.humanMoveController = new HumanMoveController(
                gameModel,
                gameView,
                (state) -> {
                    timerController.incrementTime();
                    stateLabel.setText(state.getLabel());
                },
                botMoveController,
                this::resetGame);

        initializePlayers();
    }


    /**
     * Initialization method, that is called in the Constructor and is responsible to
     * define the starting points for each player, set and update the position accordingly.
     */
    private void initializePlayers() {
        for (Player player : gameModel.getPlayers()) {
            Point startPoint = new Point(
                    gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR,
                    gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR); // * 2 is for track scale adaptation
            player.setPosition(startPoint);
            gameView.updatePlayerPosition(player, startPoint);
            if (player instanceof HumanPlayer) {
                humanMoveController.updatePlayerPosition((HumanPlayer) player, startPoint);
            }
        }
    }


    /**
     * Method to called from the {@link GameController} for reset the player position and others.
     */
    public void resetGame(GameState state) {
        Point startPoint = new Point(
                gameModel.getTrack().getStartPoint().x() * TRACK_FACTOR,
                gameModel.getTrack().getStartPoint().y() * TRACK_FACTOR);
        for (Player player : gameModel.getPlayers()) {
            player.setPosition(startPoint);
            gameView.updatePlayerPosition(player, player.getPosition());
        }
        timerController.resetTime();
        gameView.resetGame();
        humanMoveController.resetHumanPlayer();
        stateLabel.setText(state.getLabel());
    }

}
