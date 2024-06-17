package it.unicam.cs.mpmgc.formula1.model;

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
}
{
}
