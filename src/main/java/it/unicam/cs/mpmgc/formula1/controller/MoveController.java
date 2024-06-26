package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.player.BotPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.util.List;
import java.util.function.Consumer;

public class MoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private final TimerController timerController;
    private Point lastPosition;
    private final Consumer<Void> onMoveCallback;

    public MoveController(GameModel gameModel, GameView gameView, TimerController timerController, Consumer<Void> onMoveCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.timerController = timerController;
        this.onMoveCallback = onMoveCallback;
        for (Player player : gameModel.getPlayers()) {
            Point startPoint = new Point(
                    gameModel.getTrack().getStartPoint().x() * 2,
                    gameModel.getTrack().getStartPoint().y() * 2); // * 2 is for track scale adaptation
            player.setPosition(startPoint);
            updateViewPlayerPosition(player, startPoint);
            if (player instanceof HumanPlayer) {
                lastPosition = player.getPosition();
                drawMoveOptions(lastPosition);
            }
        }
    }


    private void updateViewPlayerPosition(Player player, Point lastPosition) {
        gameView.updatePlayerPosition(player, lastPosition);
    }

    private void drawMoveOptions(Point point) {
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

    protected void onMoveButtonClick(ActionEvent event) {
        Player humanPlayer = gameModel.getPlayers().getFirst();
        Button moveButton = (Button) event.getSource();
        Point newPoint = new Point((int) (moveButton.getLayoutX() ), (int) (moveButton.getLayoutY() ));

        if (gameModel.getTrack().isValidPosition(newPoint)) {
            int lastX = newPoint.x() - humanPlayer.getPosition().x();
            int lastY = newPoint.y() - humanPlayer.getPosition().y();
            Point tmp = new Point(humanPlayer.getPosition().x(), humanPlayer.getPosition().y());// store the last known position
            lastPosition = new Point(lastX, lastY); //TODO UNDERSTAND THIS
            //String x = "x";
            //String y = "y";
            //String x1 = '"' + x + '"';
            //String y1 = '"' + y + '"';
            //System.out.println("{" + x1 + ": " + newPoint.x()/20 + "," + y1 + ": " + newPoint.y()/20 + "}");
            gameModel.setPlayerPosition(humanPlayer, newPoint);
            updateViewPlayerPosition(humanPlayer, tmp);
            drawMoveOptions(newPoint);
            if (onMoveCallback != null) {
                onMoveCallback.accept(null);
            }
            updateBotPositions();
        }
    }

    private void updateBotPositions() {
        int step = timerController.getSeconds();
        for (Player player : gameModel.getPlayers()) {
            if (player instanceof BotPlayer botPlayer) {
                Point nextMove = botPlayer.getNextMove(step);
                if (nextMove != null) {
                    System.out.println(nextMove);
                    updateViewPlayerPosition(botPlayer, botPlayer.getPath().get(step - 1));
                    gameModel.setPlayerPosition(botPlayer, nextMove);
                }
            }
        }
    }

    public void resetGame() {
        for (Player player : gameModel.getPlayers()) {
            player.setPosition(gameModel.getTrack().getStartPoint());
            updateViewPlayerPosition(player, player.getPosition());
            drawMoveOptions(player.getPosition());
        }
    }

}
