package it.unicam.cs.mpmgc.formula1.model;

import java.util.List;

public class Track {
    private List<Point> points;

    public Track(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean isValidPosition(Point currentPosition, Point newPosition) {
        int currentIndex = points.indexOf(currentPosition);
        if (currentIndex == -1 || currentIndex + 2 >= points.size()) {
            return false;
        }

        // Check if the newPosition is one of the next two points
        for (int i = 1; i <= 2; i++) {
            if (points.get(currentIndex + i).equals(newPosition)) {
                return true;
            }
        }
        return false;
    }
}

