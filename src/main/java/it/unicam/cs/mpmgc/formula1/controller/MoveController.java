package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.util.function.Consumer;

public class MoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private Point lastPosition;
    private final Consumer<Void> onMoveCallback;

    public MoveController(GameModel gameModel, GameView gameView, Consumer<Void> onMoveCallback) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.onMoveCallback = onMoveCallback;
        for (Player player : gameModel.getPlayers()) {
            Point startPoint = new Point(gameModel.getTrack().getStartPoint().x() * 2, gameModel.getTrack().getStartPoint().y() *2);
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
        Player currentPlayer = gameModel.getPlayers().getFirst();
        Button moveButton = (Button) event.getSource();
        Point newPoint = new Point((int) (moveButton.getLayoutX() ), (int) (moveButton.getLayoutY() ));

        if (gameModel.getTrack().isValidPosition(newPoint)) {
            int lastX = newPoint.x() - currentPlayer.getPosition().x();
            int lastY = newPoint.y() - currentPlayer.getPosition().y();
            Point tmp = new Point(currentPlayer.getPosition().x(), currentPlayer.getPosition().y());// store the last known position
            lastPosition = new Point(lastX, lastY); //TODO UNDERSTAND THIS
            System.out.println("position:" + newPoint);
            gameModel.setPlayerPosition(currentPlayer, newPoint);
            updateViewPlayerPosition(currentPlayer, tmp);
            drawMoveOptions(newPoint);
            if (onMoveCallback != null) {
                onMoveCallback.accept(null);
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
