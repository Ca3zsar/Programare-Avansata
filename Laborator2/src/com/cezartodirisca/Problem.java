package com.cezartodirisca;

import java.util.Arrays;

public class Problem {
    private int sourceNumber;
    private int destinationNumber;

    private Source[] sources;
    private Destination[] destinations;

    private int[][] costs;
    private int totalCost;

    public Problem(int newSourceNr, int newDestNr, Source newSources[], Destination newDest[], int newCosts[][])
    {
        this.sourceNumber = newSourceNr;
        this.destinationNumber = newDestNr;
        this.sources = newSources.clone();
        this.destinations = newDest.clone();
        this.costs = newCosts.clone();
    }

    public void printCosts()
    {
        for(int i=0;i<sourceNumber;i++)
        {
            for(int j=0;j<destinationNumber;j++)
            {
                System.out.print(costs[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    @Override
    public String toString()
    {
        StringBuilder lines[] = new StringBuilder[2+sourceNumber];
        lines[0] = new StringBuilder("'%5s");



    }

}
