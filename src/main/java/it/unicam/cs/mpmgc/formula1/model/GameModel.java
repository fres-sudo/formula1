package it.unicam.cs.mpmgc.formula1.model;

import java.util.Objects;

public class GameModel {

    private int playerX;
    private int playerY;

    public GameModel() {
        playerX = 0;
        playerY = 0;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerPosition(int x, int y) {
        playerX = x;
        playerY = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameModel gameModel)) return false;
        return getPlayerX() == gameModel.getPlayerX() && getPlayerY() == gameModel.getPlayerY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerX(), getPlayerY());
    }
}
