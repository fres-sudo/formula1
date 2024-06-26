package it.unicam.cs.mpmgc.formula1.model.player;

import it.unicam.cs.mpmgc.formula1.model.point.Point;

import java.util.List;

public class BotPlayer extends Player {

    List<Point> path;

    public BotPlayer(Point startPosition, List<Point> path) {
        super(startPosition);
        this.path = path;
    }

    public List<Point> getPath() {
        return this.path;
    }

    public Point getNextMove(int step) {
        if (step < path.size()) {
            return path.get(step);
        }
        return null; // or handle the end of the path
    }

}
