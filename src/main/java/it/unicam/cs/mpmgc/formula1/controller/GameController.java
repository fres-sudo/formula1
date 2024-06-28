package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.mapper.TrackMapper;
import it.unicam.cs.mpmgc.formula1.model.parser.PathParser;
import it.unicam.cs.mpmgc.formula1.model.player.BotPlayer;
import it.unicam.cs.mpmgc.formula1.model.track.Track;
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
        List<List<Point>> paths = loadPaths();
        List<Player> players = new ArrayList<>();
        Point startingPoint = track.getStartPoint();
        players.add(new HumanPlayer(startingPoint));
        for(List<Point> path : paths) {
            players.add(new BotPlayer(startingPoint, path));
        }

        GameModel gameModel = new GameModel(track, players);
        GameView gameView = new GameView(gameModel);

        timerController = new TimerController(timerLabel);
        moveController = new MoveController(
                gameModel,
                gameView,
                timerController,
                unused -> timerController.incrementTime()
        );

        gameViewPane.getChildren().add(gameView);
    }

    @FXML
    protected void onResetRaceButtonClick() {
        moveController.resetGame();
        timerController.resetTime();
    }

    private Track loadTrack() {
        PointMapper pointMapper = new PointMapper();
        TrackMapper trackMapper = new TrackMapper(pointMapper);
        TrackParser parser = new TrackParser(trackMapper);
        try {
            return parser.parse("src/main/resources/track.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  List<List<Point>> loadPaths() {
        PointMapper pointMapper = new PointMapper();
        PathParser parser = new PathParser(pointMapper);
        try {
            return parser.parse("src/main/resources/bot_path.json");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
