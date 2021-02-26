package com.cezartodirisca;

public class Bonus {

    public static void main(String[] args) {
        int minimum=10, maximum=100;

        // Initialize the sources with random values between minimum and maximum
        Source[] sources = new Source[1000];
        for(int i=0;i<1000;i++)
        {
            int randomValue = (int)(Math.random() * (maximum - minimum + 1) + minimum);
            sources[i] = new Factory("S" + i,randomValue);
        }


        // Initialize the destinations.
        Destination[] destinations = new Destination[1000];
        for(int i=0;i<1000;i++)
        {
            int randomValue = (int)(Math.random() * (maximum - minimum + 1) + minimum);
            destinations[i] = new Destination("D" + i,randomValue);
        }

        int sourceNumber = sources.length;
        int destinationNumber = destinations.length;

        // The costs
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

        // Initialize the problem using the sources, the destinations and the costs.
        Problem problem = new Problem(sourceNumber, destinationNumber, sources, destinations, costs);

        problem.solveProblem();
        System.out.println(problem.getSolution());
    }


}
