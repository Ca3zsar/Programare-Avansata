package com.cezartodirisca;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        int playerNumber = 4;
        List<Token> tokens = new ArrayList<>();

        Random random = new Random();
        int n = random.nextInt(3) + 2; // Generate a number n between 4 and 10 to represent the dimensions of the board.

        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                tokens.add(new Token(i * j, i, j));
                tokens.add(new Token(i * j, j, i));
            }
        }


        Board board = new Board(tokens);
        Game game = new Game(board, playerNumber);

        Faker faker = new Faker();
        List<Player> players = Stream.of(
                new Player(faker.name().name(), 0, game),
                new Player(faker.name().name(), 1, game),
                new Player(faker.name().name(), 2, game),
                new Player(faker.name().name(), 3, game)
        ).collect(Collectors.toList());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(playerNumber);
        for (Player player : players) {
            executor.execute(player);
        }
        while (executor.getActiveCount() != 0) ;
        executor.shutdown();
    }
}
