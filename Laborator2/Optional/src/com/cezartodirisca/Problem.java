package com.cezartodirisca;

import java.util.Arrays;

public class Problem {
    /*
    Final modifier is used because none of the members
    can't be modified after the initialization.
     */
    private final int sourceNumber;
    private final int destinationNumber;

    private final Source[] sources;
    private final Destination[] destinations;

    private final int[][] costs;

    // Initialize the problem using the sources, the destinations and the costs.
    public Problem(int newSourceNr, int newDestNr, Source[] newSources, Destination[] newDest, int[][] newCosts) {
        this.sourceNumber = newSourceNr;
        this.destinationNumber = newDestNr;


        if(hasDuplicate(newSources,"S"))
        {
            System.out.println("Can't have duplicate sources!");
            System.exit(0);
        }

        if(hasDuplicate(newDest,"D"))
        {
            System.out.println("Can't have duplicate destinations!");
            System.exit(0);
        }

        this.sources = newSources.clone();
        this.destinations = newDest.clone();
        this.costs = newCosts.clone();
    }

    public void printCosts() {
        for (int i = 0; i < sourceNumber; i++) {
            for (int j = 0; j < destinationNumber; j++) {
                System.out.print(costs[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public boolean hasDuplicate(Object[] objArray,String type)
    {
        if(type.equals("S"))
        {
            Source[] tempSources = (Source[])objArray;
            for(int i=0;i<tempSources.length-1;i++)
            {
                for (int j=i+1;j<tempSources.length;j++) {
                    if (tempSources[i].equals(tempSources[j])) {
                        return true;
                    }
                }
            }
        }else{
            Destination[] tempDestinations = (Destination[]) objArray;
            for(int i=0;i<tempDestinations.length-1;i++)
            {
                for (int j=i+1;j<tempDestinations.length;j++) {
                    if (tempDestinations[i].equals(tempDestinations[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        /**
         * Display the instance of the problem the way it is
         * displayed in the example from the laboratory.
         */

        // lines will hold the table that will be displayed
        StringBuilder lines = new StringBuilder();

        lines.append(" ".repeat(6));
        lines.append("|");

        for (int i = 0; i < destinationNumber; i++) {
            lines.append(String.format("%4s|", destinations[i].getName()));
        }
        lines.append("Supply\n");

        for (int i = 0; i < sourceNumber; i++) {
            lines.append(String.format("%6s|", sources[i].getName()));
            for (int j = 0; j < destinationNumber; j++) {
                lines.append(String.format("%4s|", costs[i][j]));
            }
            lines.append(String.format("%6s|\n", sources[i].getSupply()));
        }

        lines.append("Demand|");
        for(int i=0;i<destinationNumber;i++)
        {
            lines.append(String.format("%4s|",destinations[i].getDemand()));
        }
        lines.append("\n");

        return lines.toString();
    }

}
