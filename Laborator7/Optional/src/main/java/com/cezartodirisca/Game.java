package com.cezartodirisca;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Board board;
    private final int playerNumber;
    volatile StringBuilder stringBuilder;
    private int actualTurn;
    private volatile int ready;
    private Map<Integer,Integer> available;

    public Game(Board board, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.actualTurn = 0;
        this.ready = 1;

        stringBuilder = new StringBuilder();

        available = new HashMap<>();
        for(int i=0;i<playerNumber;i++)
        {
            available.put(i,1);
        }
    }

    public synchronized boolean boardNotEmpty() {
        return board.getSize() != 0;
    }

    public void setReady(int value) {
        this.ready = value;
    }

    public void setAvailable(int turn,int value)
    {
        available.put(turn,value);
    }

    public synchronized Token takeToken(int turn, int wantedX, int wantedY) throws NonexistentTokenException {
        while (turn != this.actualTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(this.ready == 0 );
        ready = 0;

        Token value = null;

        if (boardNotEmpty()) {
            if(wantedX==-1) {
                value = board.getTokenValue(0);
                board.deleteToken(0);
            }else{
                if(wantedY==-1)
                {
                    for(Token token:board.getTokens())
                    {
                        if(token.getXCoordinate() == wantedX)
                        {
                            value = token;
                            break;
                        }
                    }
                    if(value!=null)
                    {
                        int index = board.getTokens().indexOf(value);
                        board.deleteToken(index);
                    }
                }else{
                    for(Token token:board.getTokens())
                    {
                        if(token.getXCoordinate() == wantedX && token.getYCoordinate()==wantedY)
                        {
                            value=token;
                            break;
                        }
                    }
                    if(value!=null)
                    {
                        int index = board.getTokens().indexOf(value);
                        board.deleteToken(index);
                    }
                }
            }
        }

        actualTurn= (actualTurn+1)%playerNumber;
        while(available.get(actualTurn) == 0)
        {
            actualTurn= (actualTurn+1)%playerNumber;
        }
        notifyAll();

        return value;
    }

    @Override
    public String toString() {
        return "The choices of players :" + stringBuilder.toString();
    }
}
