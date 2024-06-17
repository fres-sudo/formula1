package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.*;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.player.PlayerFactory;
import it.unicam.cs.mpmgc.formula1.service.JSONTrackLoader;
import it.unicam.cs.mpmgc.formula1.service.TrackLoader;
import it.unicam.cs.mpmgc.formula1.view.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class GameController {
    @FXML
    private Label currentState;

    @FXML
    private Pane gameViewPane;

    private GameEngine gameEngine;
    private MoveController moveController;

    @FXML
    public void initialize() {
        // Initialize the game view and add it to the pane
        GameView gameView = new GameView();
        gameViewPane.getChildren().add(gameView);

        // Load the track from the JSON file
        TrackLoader trackLoader = new JSONTrackLoader();
        Track track;
        try {
            track = trackLoader.loadTrack("src/main/resources/track.json");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Define the number of bots and the starting position
        int numberOfBots = 5;
        Point startPosition = track.getPoints().getFirst(); // Define a starting position

        // Create the players
        List<Player> players = PlayerFactory.createPlayers(numberOfBots, startPosition);
        Player humanPlayer = new HumanPlayer(startPosition);
        players.add(humanPlayer);

        // Initialize the game engine
        gameEngine = new GameEngine();
        gameEngine.initialize(track, players);

        currentState.setText("Game initializing, please wait...Press Start Race to start the game");

        // Connect the view (GUI) as an observer
        gameEngine.addObserver(gameView);

        // Initialize the move controller
        moveController = new MoveController(gameEngine, humanPlayer);
    }

    @FXML
    private void onStartRaceButtonClick() {
        currentState.setText("Race Started");
        gameEngine.startRace();
    }

    @FXML
    private void onStopRaceButtonClick() {
        currentState.setText("Race Stopped");
        gameEngine.stopRace();
    }

    @FXML
    private void onResetRaceButtonClick() {
        currentState.setText("Reset Race, Press Start Race for starting the game");
        this.gameEngine = new GameEngine();
    }

    // Add a method to handle key events
    public void handleKeyEvent(javafx.scene.input.KeyEvent key) {
        System.out.println(key.getCode());
        moveController.move(key.getCode());
    }
}
