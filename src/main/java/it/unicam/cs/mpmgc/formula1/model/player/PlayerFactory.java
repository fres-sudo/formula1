package it.unicam.cs.mpmgc.formula1.model.player;
import it.unicam.cs.mpmgc.formula1.model.Point;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    public static List<Player> createPlayers(int numberOfBots, Point startPosition) {
        List<Player> players = new ArrayList<>();

        players.add(new HumanPlayer(startPosition));

        for (int i = 0; i < numberOfBots; i++) {
            players.add(new BotPlayer(startPosition));
        }
        return players;
    }
}

