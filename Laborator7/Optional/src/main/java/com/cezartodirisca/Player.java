package com.cezartodirisca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Player implements Runnable {
    private final String name;
    private final int turn;
    private final List<Token> tokens;
    private final Game gameToPlay;
    private int score;
    private final String mode;

    public Player(String name, int turn, Game gameToPlay, String mode) {
        this.name = name;
        this.turn = turn;
        this.tokens = new ArrayList<>();
        this.gameToPlay = gameToPlay;
        this.mode = mode;
    }

    @Override
    public void run() {
        while (gameToPlay.boardNotEmpty()) {
            try {
                Token newToken = null;
                if(mode.equals("automate")) {
                    if (tokens.size() == 0) {
                        newToken = gameToPlay.takeToken(turn, -1,-1);
                    } else {
                        Random rand = new Random();
                        int choice = rand.nextInt(2);
                        if(choice == 0)
                        {
                            newToken = gameToPlay.takeToken(turn,tokens.get(tokens.size()-1).getYCoordinate(),tokens.get(0).getXCoordinate());
                        }else{
                            newToken = gameToPlay.takeToken(turn,tokens.get(0).getYCoordinate(),-1);
                        }
                    }
                }
                else{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String command = null;

                    System.out.print(name + ">> ");

                    try {
                        command = reader.readLine();
                    } catch (IOException exception) {
                        System.out.println("Can't read the info from the shell!");
                        exception.printStackTrace();
                    }

                    if (command == null) {
                        gameToPlay.setAvailable(turn,0);
                        gameToPlay.setReady(1);
                        return;
                    }

                    if (command.equals("")) {
                        continue;
                    }

                    List<String> arguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));
                    if(arguments.size() != 2)
                    {
                        gameToPlay.setAvailable(turn,0);
                        gameToPlay.setReady(1);
                        System.out.println("Incorrect number of coordinates");
                        return;
                    }

                    int desiredX, desiredY;

                    try {
                        desiredX = Integer.parseInt(arguments.get(0));
                        desiredY = Integer.parseInt(arguments.get(1));
                    }catch(NumberFormatException exception)
                    {
                        gameToPlay.setAvailable(turn,0);
                        gameToPlay.setReady(1);
                        exception.printStackTrace();
                        return;
                    }

                    try {
                        newToken = gameToPlay.takeToken(turn, desiredX, desiredY);
                    }catch(NonexistentTokenException exception)
                    {
                        gameToPlay.setAvailable(turn,0);
                        gameToPlay.setReady(1);
                        System.err.println(exception.getMessage());
                        return;
                    }

                    if(newToken == null)
                    {
                        System.out.println("That token is not available!");
                    }

                }

                if (newToken == null) {
                    if(!gameToPlay.boardNotEmpty())
                    {
                        System.out.println("Player " + turn + " " + name + " can't pick any token!");
                        System.out.flush();
                    }
                    gameToPlay.setAvailable(turn,0);
                    gameToPlay.setReady(1);
                    break;
                }

                tokens.add(newToken);

                int ok = 1;

                for(int i=0;i<tokens.size()-1;i++)
                {
                    if(tokens.get(i).getYCoordinate() != tokens.get(i+1).getXCoordinate())
                    {
                        ok=0;
                        break;
                    }
                }

                if(ok == 1)
                {
                    if(tokens.get(tokens.size()-1).getYCoordinate() != tokens.get(0).getXCoordinate())
                    {
                        ok =0;
                    }
                }

                if(ok == 1)
                {
                    System.out.println("Player " + turn + " " + this.name + " has finished a closed path!");
                    gameToPlay.setAvailable(turn,0);
                    gameToPlay.setReady(1);
                    return;
                }

                int xValue = tokens.get(tokens.size() - 1).getXCoordinate();
                int yValue = tokens.get(tokens.size() - 1).getYCoordinate();

                System.out.println("Player " + turn + " " + this.name + " has chosen the token : (" + xValue + ", " + yValue + ")");
                System.out.flush();
                gameToPlay.setReady(1);
            } catch (NonexistentTokenException exception) {
                exception.printStackTrace();
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void computeScore() {
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).getYCoordinate() == tokens.get(i + 1).getXCoordinate()) {
                score += tokens.get(i).getValue();
            } else {
                score = 0;
                return;
            }
        }
        if (tokens.get(tokens.size() - 1).getYCoordinate() == tokens.get(0).getXCoordinate()) {
            score += tokens.get(tokens.size() - 1).getValue();
        }
    }
}
