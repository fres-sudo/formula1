package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.player.BotPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.view.GameView;

public class BotMoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final TimerController timerController;

    public BotMoveController(GameModel gameModel, GameView gameView, TimerController timerController) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.timerController = timerController;
    }
    public void updateBotPosition() {
        int step = timerController.getSeconds();
        for (Player player : gameModel.getPlayers()) {
            if (player instanceof BotPlayer bot) {
                Point nextMove = bot.getNextMove(step);
                if (nextMove != null) {
                    gameView.updatePlayerPosition(bot, bot.getPath().get(step - 1));
                    gameModel.setPlayerPosition(bot, nextMove);
                }
            }
        }
    }
}
