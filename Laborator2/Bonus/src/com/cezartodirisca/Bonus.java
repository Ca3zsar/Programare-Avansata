package com.cezartodirisca;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Bonus {

    public static void main(String[] args) throws IOException {
        int minimum=10, maximum=100;

        FileWriter statisticsFile = null;
        try {
            statisticsFile = new FileWriter("stats.txt",false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error opening the file!");
            System.exit(0);
        }
        BufferedWriter statisticsWriter = new BufferedWriter(statisticsFile);

        long startTime = System.currentTimeMillis();
        Runtime beforeSources = Runtime.getRuntime();

        // Initialize the sources with random values between minimum and maximum
        Source[] sources = new Source[2000];
        for(int i=0;i<2000;i++)
        {
            int randomValue = (int)(Math.random() * (maximum - minimum + 1) + minimum);
            sources[i] = new Factory("S" + i,randomValue);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = (endTime-startTime);

        long afterSources = beforeSources.totalMemory() - beforeSources.freeMemory();

        //Print the time and memory needed to initialize the sources.
        try {
            statisticsWriter.write(String.format("Time needed for source initialization : %d milliseconds\n",totalTime));
            statisticsWriter.write(String.format("Memory needed for source initialization : %d B\n",afterSources));
            statisticsWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the destinations.
        startTime = System.currentTimeMillis();
        Runtime beforeDestinations = Runtime.getRuntime();

        Destination[] destinations = new Destination[2000];
        for(int i=0;i<2000;i++)
        {
            int randomValue = (int)(Math.random() * (maximum - minimum + 1) + minimum);
            destinations[i] = new Destination("D" + i,randomValue);
        }

        endTime = System.currentTimeMillis();
        totalTime = (endTime-startTime);
        long afterDestinations = beforeDestinations.totalMemory() - beforeDestinations.freeMemory();

        //Print the time needed to initialize the sources.
        try {
            statisticsWriter.write(String.format("Time needed for destination initialization : %d milliseconds\n",totalTime));
            statisticsWriter.write(String.format("Memory needed for destination initialization : %d B\n",afterDestinations));
            statisticsWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sourceNumber = sources.length;
        int destinationNumber = destinations.length;

        // The costs
        startTime = System.currentTimeMillis();
        Runtime beforeCosts = Runtime.getRuntime();

        int[][] costs = new int[sourceNumber][destinationNumber];
        minimum = 1;
        maximum = 20;

        for(int i=0;i<sourceNumber;i++)
        {
            for(int j=0;j<destinationNumber;j++)
            {
                int randomValue = (int)(Math.random() * (maximum - minimum + 1) + minimum);
                costs[i][j] = randomValue;
            }
        }

        endTime = System.currentTimeMillis();
        totalTime = (endTime-startTime);
        long afterCosts = beforeCosts.totalMemory() - beforeCosts.freeMemory();

        //Print the time and memory needed to initialize the costs.
        try {
            statisticsWriter.write(String.format("Time needed for costs initialization : %d milliseconds\n",totalTime));
            statisticsWriter.write(String.format("Memory needed for costs initialization : %d B\n",afterCosts));
            statisticsWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Initialize the problem using the sources, the destinations and the costs.
        startTime = System.currentTimeMillis();
        Runtime beforeProblem = Runtime.getRuntime();

        Problem problem = new Problem(sourceNumber, destinationNumber, sources, destinations, costs);

        problem.solveProblem();

        endTime = System.currentTimeMillis();
        totalTime = (endTime-startTime);
        long afterProblem = beforeProblem.totalMemory() - beforeProblem.freeMemory();

        //Print the time needed to solve the problem
        try {
            statisticsWriter.write(String.format("Time needed for solving the problem : %d milliseconds\n",totalTime));
            statisticsWriter.write(String.format("Memory needed for solving the problem : %d B\n",afterProblem));
        } catch (IOException e) {
            e.printStackTrace();
        }

        statisticsWriter.flush();

        System.out.println(problem.getSolution());
    }


}
