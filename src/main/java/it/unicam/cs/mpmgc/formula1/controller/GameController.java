package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.Track;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.parser.TrackParser;
import it.unicam.cs.mpmgc.formula1.model.player.HumanPlayer;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import it.unicam.cs.mpmgc.formula1.model.GameModel;
import it.unicam.cs.mpmgc.formula1.view.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    protected Pane gameViewPane;
    @FXML
    protected Label timerLabel;

    protected TimerController timerController;
    protected MoveController moveController;

    @FXML
    public void initialize() {
        Track track = loadTrack();
        List<Player> players = new ArrayList<>();
        Point startingPoint = new Point(track.getStartPoint().x() , track.getStartPoint().y() + 1); //sum is for centering
        players.add(new HumanPlayer(startingPoint));

        GameModel gameModel = new GameModel(track, players);
        GameView gameView = new GameView(gameModel);

        timerController = new TimerController(timerLabel);
        moveController = new MoveController(
                gameModel,
                gameView,
                ignored -> timerController.incrementTime());

        gameViewPane.getChildren().add(gameView);
    }

    @FXML
    protected void onResetRaceButtonClick() {
        moveController.resetGame();
        timerController.resetTime();
    }

    private Track loadTrack() {
        PointMapper pointMapper = new PointMapper();
        TrackParser parser = new TrackParser(pointMapper);
        try {
            return parser.parse("src/main/resources/track.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}