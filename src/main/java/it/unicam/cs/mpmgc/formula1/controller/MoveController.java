package it.unicam.cs.mpmgc.formula1.controller;

import it.unicam.cs.mpmgc.formula1.model.GameEngine;
import it.unicam.cs.mpmgc.formula1.model.Point;
import it.unicam.cs.mpmgc.formula1.model.player.Player;
import javafx.scene.input.KeyCode;


public class MoveController{

    private final GameEngine gameEngine;
    private final Player player;

    public MoveController(GameEngine engine, Player player){
        this.gameEngine = engine;
        this.player = player;
    }

    public void move(KeyCode direction) {
        Point currentPosition = player.getPosition();

        System.out.println("Current position: " + "[" + currentPosition.getX() + " " + currentPosition.getY() + "]");

        Point newPosition = switch (direction) {
            case W -> new Point(currentPosition.getX(), currentPosition.getY() + 1);
            case S -> new Point(currentPosition.getX(), currentPosition.getY() - 1);
            case A -> new Point(currentPosition.getX() - 1, currentPosition.getY());
            case D -> new Point(currentPosition.getX() + 1, currentPosition.getY());
            default -> currentPosition;
        };

        System.out.println("New position: " + "[" + newPosition.getX() + " " + newPosition.getY() + "]");

        //TODO: Check valid position
        //if (gameEngine.getTrack().isValidPosition(currentPosition, newPosition)) {
            player.setPosition(newPosition);
            gameEngine.notifyObservers(gameEngine.getGameState());
        //}
    }

}
