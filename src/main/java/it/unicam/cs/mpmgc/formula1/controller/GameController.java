package it.unicam.cs.mpmgc.formula1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

public class GameController {

    @FXML
    private Pane gameViewPane;
    @FXML
    private Label timerLabel;

    private TimerController timerController;
    private MoveController moveController;

    @FXML
    public void initialize() {
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView();
        timerController = new TimerController(timerLabel);
        moveController = new MoveController(gameModel, gameView);

        // Set the callback for move notifications
        moveController.setOnMoveCallback(ignored -> timerController.incrementTime());

        gameViewPane.getChildren().add(gameView);
        moveController.initialize();
    }

    @FXML
    private void onResetRaceButtonClick() {
        moveController.resetGame();
        timerController.resetTime();
    }
}
