package com.cezartodirisca;

import java.util.Arrays;

public class Optional {

    public static void main(String[] args) {
        // Initialize the sources from the example
        Source[] sources = new Source[3];
        sources[0] = new Factory("S1", 10);
        sources[1] = new Warehouse("S2", 35);
        sources[2] = new Warehouse("S3", 25);

        // Illustrate the use of toString method of Source
        System.out.println(Arrays.toString(sources));

        // Initialize the destinations from the example.
        Destination[] dests = new Destination[3];
        dests[0] = new Destination("D1",20);
        dests[1] = new Destination("D2",25);
        dests[2] = new Destination("D3",25);

        // Illustrate the use of toString method of Destination
        System.out.println(Arrays.toString(dests));

        int sourceNumber = sources.length;
        int destinationNumber = dests.length;

        // The costs from the example
        int[][] costs = {
                {2, 3, 1},
                {5, 4, 8},
                {5, 6, 8}
        };

        // Initialize the problem using the sources, the destinations and the costs.
        Problem problem = new Problem(sourceNumber, destinationNumber, sources, dests, costs);

        // Illustrate the use of toString method of Problem
        System.out.println(problem.toString());
    }


}
