package it.unicam.cs.mpmgc.formula1.model;

import it.unicam.cs.mpmgc.formula1.model.player.Player;

import java.util.List;

public class GameState {
    private final Track track;
    private final List<Player> players;
    private final int currentLap;
    private long elapsedTime;

    public GameState(Track track, List<Player> players, int currentLap, long elapsedTime) {
        this.track = track;
        this.players = players;
        this.currentLap = currentLap;
        this.elapsedTime = elapsedTime;
    }

    public Track getTrack() {
        return track;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentLap() {
        return currentLap;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long l) {
        this.elapsedTime = l;
    }
}

