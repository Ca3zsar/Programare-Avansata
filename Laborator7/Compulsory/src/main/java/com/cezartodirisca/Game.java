package com.cezartodirisca;

public class Game {
    private final Board board;
    private final int playerNumber;
    private int actualTurn;

    public Game(Board board, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.actualTurn = 0;
    }

    public boolean boardNotEmpty() {
        return board.getSize() != 0;
    }

    public synchronized int getActualTurn() {
        return actualTurn;
    }

    public synchronized void setActualTurn(int newTurn) throws InvalidValueException {
        if (newTurn < 0) {
            throw new InvalidValueException("Invalid turn!");
        }
        this.actualTurn = newTurn % playerNumber;
    }

    public synchronized Token takeToken(int turn,int index) throws NonexistentTokenException {
        while(turn != this.actualTurn)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(turn);
        Token value = board.getTokenValue(index);
        board.deleteToken(index);

        try {
            setActualTurn(turn+1);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
        notify();

        return value;
    }
}
