package com.cezartodirisca;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Optional {

    public static void main(String[] args) {
        int playerNumber = 4;
        List<Token> tokens = new ArrayList<>();

        Random random = new Random();
        int n = random.nextInt(3) * 4 + 4; // Generate a number n between 4 and 15 to represent the dimensions of the board.

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                tokens.add(new Token(i * j + 1, i, j));
                tokens.add(new Token(i * j + 1, j, i));
            }
        }

        Board board = new Board(tokens);
        Game game = new Game(board, playerNumber);

        Faker faker = new Faker();
        List<Player> players = Stream.of(
                new Player("Cezar", 0, game, "manual"),
                new Player(faker.name().name(), 1, game, "automate"),
                new Player(faker.name().name(), 2, game, "automate"),
                new Player(faker.name().name(), 3, game, "automate")
        ).collect(Collectors.toList());

        System.out.println("Table size : " + n);
        System.out.println("Each token's value is x * y + 1");

        TimeKeeper timeKeeper = new TimeKeeper();
        timeKeeper.setDaemon(true);
        timeKeeper.start();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(playerNumber);
        for (Player player : players) {
            executor.execute(player);
        }

        while (executor.getActiveCount() != 0 && timeKeeper.isAlive()) ;
        if (timeKeeper.isAlive()) {
            timeKeeper.isRunning = false;
            System.out.println("Time elapsed : " + timeKeeper.elapsedSeconds + " seconds");
        } else {
            System.out.println("Time exceeded!");
        }
        executor.shutdownNow();

        for (Player player : players) {
            player.computeScore();
            System.out.println(player.getScore());
        }
    }
}
