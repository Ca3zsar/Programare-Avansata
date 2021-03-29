package com.cezartodirisca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    private final Board board;
    private final int playerNumber;
    private final Map<Integer, Integer> available;
    volatile StringBuilder stringBuilder;
    private int actualTurn;
    private volatile int ready;

    public Game(Board board, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.actualTurn = 0;
        this.ready = 1;

        stringBuilder = new StringBuilder();

        available = new HashMap<>();
        for (int i = 0; i < playerNumber; i++) {
            available.put(i, 1);
        }
    }

    public synchronized boolean boardNotEmpty() {
        return board.getSize() != 0;
    }

    public void setReady(int value) {
        this.ready = value;
    }

    public void setAvailable(int turn, int value) {
        available.put(turn, value);
    }

    public synchronized List<Integer> choices(int turn) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = null;

        System.out.print("Player " + turn + ">> ");

        try {
            command = reader.readLine();
        } catch (IOException exception) {
            System.out.println("Can't read the info from the shell!");
            exception.printStackTrace();
        }

        if (command == null || command.equals("\n")) {
            setAvailable(turn, 0);
            setReady(1);
            return null;
        }

        List<String> arguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));
        if (arguments.size() != 2) {
            setAvailable(turn, 0);
            setReady(1);
            System.out.println("Incorrect number of coordinates");
            return null;
        }

        int desiredX, desiredY;

        try {
            desiredX = Integer.parseInt(arguments.get(0));
            desiredY = Integer.parseInt(arguments.get(1));
        } catch (NumberFormatException exception) {
            setAvailable(turn, 0);
            setReady(1);
            exception.printStackTrace();
            return null;
        }

        List<Integer> toReturn = new ArrayList<>();
        toReturn.add(desiredX);
        toReturn.add(desiredY);

        return toReturn;
    }

    public synchronized Token takeToken(int turn, int wantedX, int wantedY, String newMode) throws NonexistentTokenException {
        while (turn != this.actualTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (this.ready == 0) ;
        ready = 0;

        if (newMode.equals("manual")) {
            List<Integer> choices = choices(turn);
            wantedX = choices.get(0);
            wantedY = choices.get(1);
        }

        Token value = null;

        if (boardNotEmpty()) {
            if (wantedX == -1) {
                value = board.getTokenValue(0);
                board.deleteToken(0);
            } else {
                if (wantedY == -1) {
                    for (Token token : board.getTokens()) {
                        if (token.getXCoordinate() == wantedX) {
                            value = token;
                            break;
                        }
                    }
                    if (value != null) {
                        int index = board.getTokens().indexOf(value);
                        board.deleteToken(index);
                    }
                } else {
                    for (Token token : board.getTokens()) {
                        if (token.getXCoordinate() == wantedX && token.getYCoordinate() == wantedY) {
                            value = token;
                            break;
                        }
                    }
                    if (value != null) {
                        int index = board.getTokens().indexOf(value);
                        board.deleteToken(index);
                    }
                }
            }
        }

        actualTurn = (actualTurn + 1) % playerNumber;
        while (available.get(actualTurn) == 0) {
            actualTurn = (actualTurn + 1) % playerNumber;
        }
        notifyAll();

        return value;
    }

    @Override
    public String toString() {
        return "The choices of players :" + stringBuilder.toString();
    }
}
