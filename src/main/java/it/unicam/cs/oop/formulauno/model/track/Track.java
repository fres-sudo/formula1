package it.unicam.cs.oop.formulauno.model.track;

import it.unicam.cs.oop.formulauno.model.point.Point;

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

    /**
     * Helper method to check if a point is a valid point within the track,
     * the method also consider a range of tolerance.
     * The normalization of the coordinates is due to a json inconsistency since the json of the
     * valid positions doesn't consider "middle" point (points ending with a 5) but they are valid
     * position tho, the data must point must be "normalized" before checking his position
     *
     * @param point the point to check the position
     * @return true if the point is in a valid position or false if not
     */
    public boolean isValidPosition(Point point) {

        for (Point validPoint : validPositions) {

            validPoint = new Point(validPoint.x() * TRACK_FACTOR, validPoint.y() * TRACK_FACTOR);

            if (point.x() % 10 != 0) { //normalization of X
                point = new Point(point.x() + 5, point.y());
            }
            if (point.y() % 10 != 0) { // normalization of Y
                point = new Point(point.x(), point.y() + 5);
            }

            boolean validX = point.x() - TOLERANCE <= validPoint.x() && point.x() + TOLERANCE >= validPoint.x();
            boolean validY = point.y() - TOLERANCE <= validPoint.y() && point.y() + TOLERANCE >= validPoint.y();

            if (validX && validY) {
                return true;
            }
        }
        return false;
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

