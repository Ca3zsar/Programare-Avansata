package com.cezartodirisca;

public class Game {
    private final Board board;
    private final int playerNumber;
    volatile StringBuilder stringBuilder;
    private int actualTurn;
    private volatile int ready;

    public Game(Board board, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.actualTurn = 0;
        this.ready = 1;

        stringBuilder = new StringBuilder();
    }

    public synchronized boolean boardNotEmpty() {
        return board.getSize() != 0;
    }

    public void setReady(int value) {
        this.ready = value;
    }

    public synchronized Token takeToken(int turn, int index) throws NonexistentTokenException {
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
            value = board.getTokenValue(index);
            board.deleteToken(index);
        }

        actualTurn= (actualTurn+1)%playerNumber;
        notifyAll();

        return value;
    }

    @Override
    public String toString() {
        return "The choices of players :" + stringBuilder.toString();
    }
}
