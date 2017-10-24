package com.michael.educationaltictactoe;

import java.io.Serializable;

/**
 * Created by cdu on 2016-05-02.
 */
public class Player {
    private String name;
    private boolean isPlayerX;
    private boolean isMyTurn;
    private int score;

    public Player(String name, boolean isPlayerX, boolean isMyTurn, int score){
        this.name = name;
        this.isPlayerX = isPlayerX;
        this.isMyTurn = isMyTurn;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlayerX() {
        return this.isPlayerX;
    }

    public void setIsPlayerX(boolean isPlayerX) {
        this.isPlayerX = isPlayerX;
    }

    public boolean isMyTurn() {
        return this.isMyTurn;
    }

    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(){
        this.score++;
    }


    @Override
    public String toString(){
        return "Player name: " + getName() + "\n" +
                "player is X: " + isPlayerX()  + "\n" +
                "My turn: " + isMyTurn() + "\n" +
                "player score: " + getScore();
    }
}
