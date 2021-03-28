package com.cezartodirisca;

import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable {
    private final String name;
    private final int turn;
    private final List<Token> tokens;
    private final Game gameToPlay;

    public Player(String name, int turn, Game gameToPlay) {
        this.name = name;
        this.turn = turn;
        this.tokens = new ArrayList<>();
        this.gameToPlay = gameToPlay;
    }

    @Override
    public void run() {
        while (gameToPlay.boardNotEmpty()) {
            if (gameToPlay.boardNotEmpty()) {
                try {
                    tokens.add(gameToPlay.takeToken(turn,0));

                    int xValue = tokens.get(tokens.size()-1).getXCoordinate();
                    int yValue = tokens.get(tokens.size()-1).getYCoordinate();

                    System.out.println("Player " + this.name + " has chosen the token : (" + xValue + ", " + yValue + ")");
                } catch (NonexistentTokenException exception) {
                    exception.printStackTrace();
                }
            }else{
                break;
            }
        }
    }
}
