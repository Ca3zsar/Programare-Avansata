package com.cezartodirisca;

public class Problem {
    private final int sourceNumber;
    private final int destinationNumber;

    private final Source[] sources;
    private final Destination[] destinations;

    private final int[][] costs;
    private int totalCost;

    public Problem(int newSourceNr, int newDestNr, Source[] newSources, Destination[] newDest, int[][] newCosts) {
        this.sourceNumber = newSourceNr;
        this.destinationNumber = newDestNr;
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

    @Override
    public String toString() {
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < 6; i++) lines.append(" ");
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
