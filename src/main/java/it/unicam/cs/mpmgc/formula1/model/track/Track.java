package it.unicam.cs.mpmgc.formula1.model.track;

import it.unicam.cs.mpmgc.formula1.model.point.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Track {

    public static final int TRACK_FACTOR = 2;
    private static final int TOLERANCE = 10;
    private final List<Point> outerTrack;
    private final List<Point> innerTrack;
    private final List<Point> points;
    private final List<Point> validPositions;
    private final Point startingPoint;

    public Track(List<Point> innerTrack, List<Point> outerTrack, List<Point> points, List<Point> validPositions, Point startingPoint) {
        this.innerTrack = innerTrack;
        this.outerTrack = outerTrack;
        this.points = points;
        this.validPositions = validPositions;
        this.startingPoint = startingPoint;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getStartPoint() {
        return startingPoint;
    }

    public List<Point> getInnerTrack() {
        return innerTrack;
    }

    public List<Point> getOuterTrack() {
        return outerTrack;
    }

    public List<Point> getValidPositions() {
        return validPositions;
    }

    public boolean isRaceEnded(Point playerPosition) {

        int tolerance = TOLERANCE * TRACK_FACTOR; // Tolerance range, multiplied by the TRACK_FACTOR to enlighten the scaling
        int startX = startingPoint.x() * TRACK_FACTOR;
        int startY = startingPoint.y() * TRACK_FACTOR;

        boolean isNotExactStartingPoint = playerPosition.x() != startX || playerPosition.y() != startY;
        boolean isBeforeStartingPoint = playerPosition.x() < startX && playerPosition.y() > startY - tolerance && playerPosition.y() < startY + tolerance;

        return isNotExactStartingPoint && isBeforeStartingPoint;
    }


    public boolean isValidPosition(Point point) {

        for (Point validPoint : validPositions) {
            if (Math.abs(point.x() - validPoint.x()) <= TOLERANCE && Math.abs(point.x() - validPoint.y()) <= TOLERANCE) {
                return true;
            }
        }
        return false;

        //return validPositions.contains(point);
    }


    @Override
    public String toString() {
        return "Track{" +
                "points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track track)) return false;
        return Objects.equals(getPoints(), track.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPoints());
    }

}

