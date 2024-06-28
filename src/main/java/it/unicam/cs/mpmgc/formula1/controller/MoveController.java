package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.player.BotPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.util.function.Consumer;

public class MoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final HumanMoveController humanMoveController;
    private final BotMoveController botMoveController;

    public MoveController(GameModel gameModel, GameView gameView, TimerController timerController, Consumer<Void> onMoveCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.botMoveController = new BotMoveController(gameModel, gameView, timerController);
        this.humanMoveController = new HumanMoveController(gameModel, gameView, onMoveCallback, botMoveController);

        initializePlayers();
    }


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
