package it.unicam.cs.oop.formulauno.controller;

import it.unicam.cs.oop.formulauno.model.GameModel;
import it.unicam.cs.oop.formulauno.model.player.BotPlayer;
import it.unicam.cs.oop.formulauno.model.player.Player;
import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.view.GameView;

import java.util.function.Consumer;


/**
 * Describes the movement of a Bot Player
 */
public class BotMoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final TimerController timerController;
    private final Consumer<GameState> resetGameCallback;

    /**
     * Constructor method for BotMoveController to initialize all the actors of the controller.
     *
     * @param gameModel       the instance of the {@link GameModel}
     * @param gameView        the instance of the {@link GameView}
     * @param timerController the instance of TimerController for finding the position of the bot
     */
    public BotMoveController(GameModel gameModel, GameView gameView, TimerController timerController, Consumer<GameState> resetGameCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.timerController = timerController;
        this.resetGameCallback = resetGameCallback;
    }

    /**
     * This method move a Bot Player by updating his position.
     */
    public void updateBotPosition() {
        int step = timerController.getSeconds();
        for (Player player : gameModel.getPlayers()) {
            if (player instanceof BotPlayer bot) {
                Point nextMove = bot.getNextMove(step);
                if (nextMove != null) {
                    if (gameModel.getTrack().isRaceEnded(nextMove)) {
                        resetGameCallback.accept(GameState.LOST);
                    } else {
                        gameView.updatePlayerPosition(bot, bot.getPath().get(step - 1));
                        gameModel.setPlayerPosition(bot, nextMove);
                    }
                }
            }
        }
    }
}
