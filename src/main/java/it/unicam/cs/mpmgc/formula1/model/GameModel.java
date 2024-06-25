package it.unicam.cs.mpmgc.formula1.model;

import it.unicam.cs.mpmgc.formula1.model.player.Player;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.track.Track;

import java.util.List;
import java.util.Objects;

public class GameModel {

    private List<Player> players;
    private Track track;

    public GameModel(Track track, List<Player> players) {
        this.track = track;
        this.players = players;
    }

    public void setPlayerPosition(Player player,Point point) {
        for(Player p : players){
            if (p.equals(player)) {
                p.setPosition(point);
            }
        }
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
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
