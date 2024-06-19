package it.unicam.cs.mpmgc.formula1.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.util.function.Consumer;

public class MoveController {

    private GameModel gameModel;
    private GameView gameView;
    private int previousDx = 0;
    private int previousDy = 0;
    private Consumer<Void> onMoveCallback;

    public MoveController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    public void setOnMoveCallback(Consumer<Void> onMoveCallback) {
        this.onMoveCallback = onMoveCallback;
    }

    public void initialize() {
        updateViewPlayerPosition(gameModel.getPlayerX(), gameModel.getPlayerY());
        drawMoveOptions(gameModel.getPlayerX(), gameModel.getPlayerY());
    }

    private void updateViewPlayerPosition(int x, int y) {
        gameView.updatePlayerPosition(x, y);
    }

    private void drawMoveOptions(int x, int y) {
        int[][] directions = {
                {previousDx, previousDy}, {previousDx - 1, previousDy}, {previousDx + 1, previousDy},
                {previousDx, previousDy - 1}, {previousDx, previousDy + 1},
                {previousDx - 1, previousDy - 1}, {previousDx - 1, previousDy + 1},
                {previousDx + 1, previousDy - 1}, {previousDx + 1, previousDy + 1}
        };
        gameView.drawMoveOptions(x, y, directions, this::onMoveButtonClick);
    }

    private void onMoveButtonClick(ActionEvent event) {
        Button moveButton = (Button) event.getSource();
        int newX = (int) (moveButton.getLayoutX() / 10);
        int newY = (int) (moveButton.getLayoutY() / 10);
        previousDx = newX - gameModel.getPlayerX();
        previousDy = newY - gameModel.getPlayerY();
        gameModel.setPlayerPosition(newX, newY);
        updateViewPlayerPosition(newX, newY);
        drawMoveOptions(newX, newY);
        if (onMoveCallback != null) {
            onMoveCallback.accept(null);
        }
    }

    public void resetGame() {
        gameModel.setPlayerPosition(0, 0);
        updateViewPlayerPosition(0, 0);
        drawMoveOptions(0, 0);
    }
}
