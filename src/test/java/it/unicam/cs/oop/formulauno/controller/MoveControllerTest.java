package it.unicam.cs.oop.formulauno.controller;
import it.unicam.cs.oop.formulauno.FXTest;
import it.unicam.cs.oop.formulauno.model.GameModel;
import it.unicam.cs.oop.formulauno.model.player.HumanPlayer;
import it.unicam.cs.oop.formulauno.model.player.Player;
import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.model.track.Track;
import it.unicam.cs.oop.formulauno.view.GameView;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static it.unicam.cs.oop.formulauno.model.track.Track.TRACK_FACTOR;
import static org.junit.jupiter.api.Assertions.*;

class MoveControllerTest extends FXTest {

    private MoveController moveController;
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        List<Point> innerTrack = List.of(new Point(5, 5), new Point(10, 15), new Point(15, 10), new Point(20, 20));
        List<Point> outerTrack = List.of(new Point(10, 10), new Point(10, 5), new Point(20, 5), new Point(10, 30));
        List<Point> validPositions = List.of(new Point(5,5), new Point(10,10), new Point(20,20), new Point(30,30), new Point(40,40));
        List<Point> points = Stream.concat(innerTrack.stream(), outerTrack.stream()).toList();
        Point startingPoint = new Point(10,10);
        Track track = new Track(innerTrack, outerTrack, points,validPositions, startingPoint);

        gameModel = new GameModel(track, List.of(new HumanPlayer(startingPoint)));
        GameView gameView = new GameView(gameModel);

        Label label = new Label();
        TimerController timerController = new TimerController(label);
        moveController = new MoveController(gameModel, gameView, timerController, label);
    }

    @Test
    void testInitializePlayers() {
        moveController.initializePlayers();
        Player player = gameModel.getPlayers().getFirst();
        assertEquals(new Point(10 * TRACK_FACTOR, 10 * TRACK_FACTOR), player.getPosition());
    }

    @Test
    void testResetGame() {
        moveController.resetGame(GameState.RESET);
        Player player = gameModel.getPlayers().getFirst();
        assertEquals(new Point(10 * TRACK_FACTOR, 10 * TRACK_FACTOR), player.getPosition());
    }
}
