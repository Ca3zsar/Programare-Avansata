package com.cezartodirisca;

public class Game {
    private final Board board;
    private final int playerNumber;
    StringBuilder stringBuilder;
    private int actualTurn;
    private int ready;

    public Game(Board board, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.actualTurn = 0;
        this.ready = 1;

        stringBuilder = new StringBuilder();
    }

    public boolean boardNotEmpty() {
        return board.getSize() != 0;
    }

    public void setReady(int value) {
        this.ready = value;
    }

    public void setBuffer(String toAdd) {
        stringBuilder.append(toAdd).append("\n");
    }

    public synchronized void setActualTurn(int newTurn) throws InvalidValueException {
        if (newTurn < 0) {
            throw new InvalidValueException("Invalid turn!");
        }
        this.actualTurn = newTurn % playerNumber;
    }

    public synchronized Token takeToken(int turn, int index) throws NonexistentTokenException {
        while (turn != this.actualTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (this.ready == 0) ;

        ready = 0;

        if (boardNotEmpty()) {
            Token value = board.getTokenValue(index);
            board.deleteToken(index);

            try {
                setActualTurn(turn + 1);
            } catch (InvalidValueException e) {
                e.printStackTrace();
            }
            notifyAll();

            return value;
        }

        try {
            setActualTurn(turn + 1);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
        notifyAll();

        return null;
    }

    @Override
    public String toString() {
        return "The choices of players :" + stringBuilder.toString();
    }
}
