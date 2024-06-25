package it.unicam.cs.mpmgc.formula1.model.track;

import it.unicam.cs.mpmgc.formula1.model.point.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Track {
    private List<Point> outerTrack;
    private List<Point> innerTrack;
    private List<Point> points;
    private Point startingPoint;

    public Track(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getStartPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public List<Point> getInnerTrack() {
        return innerTrack;
    }
    public List<Point> getOuterTrack() {
        return outerTrack;
    }

    public boolean isValidPosition(Point point) {
        return true; //isPointInPolygon(point, outerTrack) && !isPointInPolygon(point, innerTrack);
    }

    private boolean isPointInPolygon(Point point, List<Point> polygon) {
        int i, j;
        boolean result = false;
        for (i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
            if ((polygon.get(i).y() > point.y()) != (polygon.get(j).y() > point.y()) &&
                    (point.x() < (polygon.get(j).x() - polygon.get(i).x()) * (point.y() - polygon.get(i).y()) / (polygon.get(j).y() - polygon.get(i).y()) + polygon.get(i).x())) {
                result = !result;
            }
        }
        return result;
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

