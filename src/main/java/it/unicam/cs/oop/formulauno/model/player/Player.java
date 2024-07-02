package it.unicam.cs.oop.formulauno.model.player;

import it.unicam.cs.oop.formulauno.model.point.Point;

/**
 * Abstract class of a Player that defines how a Player should work.
 */
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

