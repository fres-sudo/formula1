package it.unicam.cs.oop.formulauno.model;

import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.model.track.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TrackTest {

    private Track track;

    @BeforeEach
    public void setUp() {
        List<Point> innerTrack = List.of(new Point(5, 5), new Point(10, 15), new Point(15, 10), new Point(20, 20));
        List<Point> outerTrack = List.of(new Point(10, 10), new Point(10, 5), new Point(20, 5), new Point(10, 30));
        List<Point> validPositions = List.of(new Point(5,5), new Point(10,10), new Point(20,20), new Point(30,30), new Point(40,40));
        List<Point> points = Stream.concat(innerTrack.stream(), outerTrack.stream()).toList();
        Point startingPoint = new Point(10,10);
        this.track = new Track(innerTrack, outerTrack, points,validPositions, startingPoint);
    }

    @Test
    void testValidPosition(){
        Point point1 = new Point(15,15);
        Point point2 = track.getValidPositions().getFirst();

        Point point3 = new Point(95,95);
        Point point4 = new Point(5,-80);


        assertTrue(track.isValidPosition(point1));
        assertTrue(track.isValidPosition(point2));

        assertFalse(track.isValidPosition(point3));
        assertFalse(track.isValidPosition(point4));
    }

    @Test
    void testRaceEnded(){
        Point point1 = new Point(10,10);
        Point point2 = new Point(40,50);
        Point point3 = new Point(15,20);

        assertTrue(track.isRaceEnded(point1));
        assertFalse(track.isRaceEnded(point2));
        assertTrue(track.isRaceEnded(point3));
    }

}
