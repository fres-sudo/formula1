package it.unicam.cs.mpmgc.formula1.model.player;

import it.unicam.cs.mpmgc.formula1.model.point.Point;

public abstract class Player {
    protected Point position;

    public Player(Point startPosition) {
        this.position = startPosition;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}

