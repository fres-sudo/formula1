package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    public void testLoadTrack() {
        Track track = gameController.loadTrack();
        assertNotNull(track);
        assertFalse(track.getPoints().isEmpty());
    }

    @Test
    public void testLoadPaths() {
        List<List<Point>> paths = gameController.loadPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
    }
}