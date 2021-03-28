package com.cezartodirisca;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	    int playerNumber = 4;
	    List<Token> tokens = new ArrayList<>();

        Random random = new Random();
        int n = random.nextInt(7) + 4; // Generate a number n between 4 and 10 to represent the dimensions of the board.

        int numberOfTokens = random.nextInt((n*n)/4) + 4;
        int counter = 0;

        while(counter<numberOfTokens)
        {
            int x = random.nextInt(n);
            int y = random.nextInt(n);
            int value = random.nextInt(10);
            int found=-1;

            for(Token token:tokens)
            {
                if(token.getXCoordinate() == x && token.getYCoordinate() == y)
                {
                    found=1;
                    break;
                }
            }

            if(found == -1)
            {
                counter++;
                tokens.add(new Token(value,x,y));
            }
        }

        Board board = new Board(tokens);
        Game game = new Game(board,playerNumber);

        Faker faker = new Faker();
        List<Player>players = Stream.of(
                        new Player(faker.name().name(),0,game),
                        new Player(faker.name().name(),1,game),
                        new Player(faker.name().name(),2,game),
                        new Player(faker.name().name(),3,game)
                    ).collect(Collectors.toList());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(playerNumber);
        for(Player player:players)
        {
            executor.execute(player);
        }
    }
}
