package it.unicam.cs.mpmgc.formula1.model;

import it.unicam.cs.mpmgc.formula1.model.player.Player;

import java.util.List;

public interface Engine<T, P> {

    void initialize(Track track, List<Player> players);

    void startRace();

    void stopRace();
}
