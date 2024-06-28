package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.util.function.Consumer;

/**
 * Main Class to handle movements
 */
public class MoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final HumanMoveController humanMoveController;
    private final BotMoveController botMoveController;

    /**
     * The Constructor of the Move Controller manages both model, view and other sub-movement controllers
     *
     * @param gameModel instance of {@link GameModel}
     * @param gameView instance of {@link GameView}
     * @param timerController instance of {@link TimerController}
     * @param onMoveCallback call back to notify the Timer
     */
    public MoveController(GameModel gameModel, GameView gameView, TimerController timerController, Consumer<Void> onMoveCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.botMoveController = new BotMoveController(gameModel, gameView, timerController);
        this.humanMoveController = new HumanMoveController(gameModel, gameView, onMoveCallback, botMoveController);

        initializePlayers();
    }


    /**
     * Initialization method, that is called in the Constructor and is responsible to
     * define the starting points for each player, set and update the position accordingly.
     */
    private void initializePlayers() {
        for (Player player : gameModel.getPlayers()) {
            Point startPoint = new Point(
                    gameModel.getTrack().getStartPoint().x() * 2,
                    gameModel.getTrack().getStartPoint().y() * 2); // * 2 is for track scale adaptation
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
    public void resetGame() {
        for (Player player : gameModel.getPlayers()) {
            Point startPoint = gameModel.getTrack().getStartPoint();
            player.setPosition(startPoint);
            gameView.updatePlayerPosition(player, player.getPosition());
            if (player instanceof HumanPlayer) {
                 humanMoveController.drawMoveOptions(startPoint);
            }
        }
    }

}
