package it.unicam.cs.mpmgc.formula1.model;

import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.track.Track;

import java.util.List;
import java.util.Objects;

/**
 * Core Class that defines the general structure of a Game
 */
public class GameModel {

    private List<Player> players;
    private Track track;

    /**
     * Constructor method to initialize all the necessary parts of a Game
     *
     * @param track the track of a specific Game
     * @param players the players of a specific Game
     */
    public GameModel(Track track, List<Player> players) {
        this.track = track;
        this.players = players;
    }

    public void setPlayerPosition(Player player, Point point) {
        players.get(players.indexOf(player)).setPosition(point);
    }

    public Track getTrack() {
        return track;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameModel gameModel)) return false;
        return Objects.equals(getPlayers(), gameModel.getPlayers()) && Objects.equals(getTrack(), gameModel.getTrack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayers(), getTrack());
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "players=" + players +
                ", track=" + track +
                '}';
    }

}
