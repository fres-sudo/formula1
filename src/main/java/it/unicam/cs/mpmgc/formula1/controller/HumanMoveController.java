package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.view.GameView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class HumanMoveController {

    private final GameModel gameModel;
    private final GameView gameView;
    private Point lastPosition;
    private final Consumer<Void> onMoveCallback;
    private final BotMoveController botMoveController;

    public HumanMoveController(GameModel gameModel, GameView gameView, Consumer<Void> onMoveCallback, BotMoveController botMoveController) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.onMoveCallback = onMoveCallback;
        this.lastPosition = new Point(
                gameModel.getTrack().getStartPoint().x() * 2,
                gameModel.getTrack().getStartPoint().y() * 2); // * 2 for track scale adaptation
        this.botMoveController = botMoveController;
    }

    public void updatePlayerPosition(HumanPlayer humanPlayer, Point lastPosition) {
        gameView.updatePlayerPosition(humanPlayer, lastPosition);
        drawMoveOptions(humanPlayer.getPosition());
    }

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

    private void onMoveButtonClick(ActionEvent event) {
        HumanPlayer humanPlayer = (HumanPlayer) gameModel.getPlayers().getFirst();
        Button moveButton = (Button) event.getSource();
        Point newPoint = new Point((int) moveButton.getLayoutX(), (int) moveButton.getLayoutY());

        if (gameModel.getTrack().isValidPosition(newPoint)) {
            int lastX = newPoint.x() - humanPlayer.getPosition().x();
            int lastY = newPoint.y() - humanPlayer.getPosition().y();
            Point tmp = new Point(humanPlayer.getPosition().x(), humanPlayer.getPosition().y());// store the last known position
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
