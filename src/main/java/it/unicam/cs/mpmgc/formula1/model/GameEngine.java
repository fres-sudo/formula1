package it.unicam.cs.mpmgc.formula1.model;// GameEngine.java
import it.unicam.cs.mpmgc.formula1.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Engine<Track, Player>, Observable<GameState> {
    private GameState gameState;
    private final List<Observer<GameState>> observers;
    private final long startTime;

    public GameEngine() {
        this.observers = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void initialize(Track track, List<Player> players) {
        this.gameState = new GameState(track, players, 0, 0);
    }

    @Override
    public void startRace() {
        this.gameState.setElapsedTime(System.currentTimeMillis() - startTime);
        notifyObservers(this.gameState);
    }

    @Override
    public void stopRace() {
        // Logic to stop the race
    }

    @Override
    public void addObserver(Observer<GameState> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<GameState> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameState state) {
        for (Observer<GameState> observer : observers) {
            observer.update(state);
        }
    }

    public Track getTrack() {
        return gameState.getTrack();
    }

    public GameState getGameState() {
        return gameState;
    }
}