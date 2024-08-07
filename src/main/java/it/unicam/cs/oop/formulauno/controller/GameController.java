package it.unicam.cs.oop.formulauno.controller;

import it.unicam.cs.oop.formulauno.model.GameModel;
import it.unicam.cs.oop.formulauno.model.mapper.PointMapper;
import it.unicam.cs.oop.formulauno.model.mapper.TrackMapper;
import it.unicam.cs.oop.formulauno.model.parser.PathParser;
import it.unicam.cs.oop.formulauno.model.parser.TrackParser;
import it.unicam.cs.oop.formulauno.model.player.BotPlayer;
import it.unicam.cs.oop.formulauno.model.player.HumanPlayer;
import it.unicam.cs.oop.formulauno.model.player.Player;
import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.model.track.Track;
import it.unicam.cs.oop.formulauno.view.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.oop.formulauno.MainApp.*;

/**
 * This is the core controller of the application, it manages and initialize all the other controllers.
 */
public class GameController {

    @FXML
    protected Pane gameViewPane;
    @FXML
    protected Label timerLabel;
    @FXML
    protected Label stateLabel;

    protected TimerController timerController;
    protected MoveController moveController;

    /**
     * JavaFx method that runs whenever the game is initialized.
     * This method is responsible to load all the necessary json file
     * and to initialize and inject all the controllers.
     */
    @FXML
    public void initialize() {
        gameViewPane.setMaxSize(GAME_HEIGHT, GAME_WIDTH);

        Track track = loadTrack();
        List<List<Point>> paths = loadPaths();
        List<Player> players = new ArrayList<>();
        Point startingPoint = track.getStartPoint();
        players.add(new HumanPlayer(startingPoint));
        for (List<Point> path : paths) {
            players.add(new BotPlayer(startingPoint, path));
        }
        GameModel gameModel = new GameModel(track, players);
        GameView gameView = new GameView(gameModel);

        timerController = new TimerController(timerLabel);
        moveController = new MoveController(gameModel, gameView, timerController, stateLabel);

        stateLabel.setText(GameState.INIT.getLabel());
        gameViewPane.getChildren().add(gameView);
    }

    /**
     * This method is called from the UI and reset the game of it initial state.
     */
    @FXML
    protected void onResetRaceButtonClick() {
        moveController.resetGame(GameState.RESET);
        timerController.resetTime();
    }

    /**
     * Implementation of the {@link TrackParser}
     *
     * @return instance of a Track Object
     */
    protected Track loadTrack() {
        PointMapper pointMapper = new PointMapper();
        TrackMapper trackMapper = new TrackMapper(pointMapper);
        TrackParser parser = new TrackParser(trackMapper);
        try {
            return parser.parse("src/main/resources/track.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Implementation of the {@link PathParser}
     *
     * @return instance of a List of all paths of the Bots
     */
    protected List<List<Point>> loadPaths() {
        PointMapper pointMapper = new PointMapper();
        PathParser parser = new PathParser(pointMapper);
        try {
            return parser.parse("src/main/resources/bot_path.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

