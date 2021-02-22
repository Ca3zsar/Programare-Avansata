package com.cezartodirisca;

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
    private Solution solution;

    /**
     * @param newSourceNr - the number of the sources
     * @param newDestNr - the number of the destinations
     * @param newSources - an array of Sources
     * @param newDest - an array of destinations
     * @param newCosts - the cost matrix, representing the cost to deliver an unit from a source to a specific destination.
     */

    public Problem(int newSourceNr, int newDestNr, Source[] newSources, Destination[] newDest, int[][] newCosts) {

        this.sourceNumber = newSourceNr;
        this.destinationNumber = newDestNr;
        this.solution = null;

        if (hasDuplicate(newSources, "S")) {
            System.out.println("Can't have duplicate sources!");
            System.exit(0);
        }

        if (hasDuplicate(newDest, "D")) {
            System.out.println("Can't have duplicate destinations!");
            System.exit(1);
        }

        this.sources = newSources.clone();
        this.destinations = newDest.clone();
        this.costs = newCosts.clone();
    }

    /**
     * Function used to determine if the objArray has any duplicates.
     * @param objArray - the array of Sources or Destinations
     * @param type - is "S" or "D" depending on its type ( S for Sources, D for Destinations)
     * @return Returns true if there is a duplicate in the array, false otherwise
     */
    public boolean hasDuplicate(Object[] objArray, String type) {
        if (type.equals("S")) {
            Source[] tempSources = (Source[]) objArray;
            for (int i = 0; i < tempSources.length - 1; i++) {
                for (int j = i + 1; j < tempSources.length; j++) {
                    if (tempSources[i].equals(tempSources[j])) {
                        return true;
                    }
                }
            }
        } else {
            Destination[] tempDestinations = (Destination[]) objArray;
            for (int i = 0; i < tempDestinations.length - 1; i++) {
                for (int j = i + 1; j < tempDestinations.length; j++) {
                    if (tempDestinations[i].equals(tempDestinations[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return a solution only if the problem was already solved.
     * @return Returns the solution if there is one, otherwise exits.
     */
    public Solution getSolution() {

        if (solution == null) {
            System.out.println("Problem not solved yet!");
            System.exit(1);
        }
        return solution;
    }

    /**
     * In order to get a feasible solution, the Transportation Problem the Least Cost Method
     * will be used. At every step the minimum cost of delivery will be chosen.
     * In case that there is a tie, the cell where the maximum quantity can be delivered
     * will be chosen. Repeat this process until the demand is satisfied.
     */
    public void solveProblem() {
        // The problem can be solved only once because the solver will alter the data from the problem.
        if (solution != null) {
            System.out.println("Problem already solved! Try using 'getSolution'.");
            return;
        }

        int minimumCost;
        int finalCost = 0;
        int quantity;
        int source, dest;

        StringBuilder solutionBuilder = new StringBuilder("");

        int totalSupply = 0;
        for (int i = 0; i < sourceNumber; i++) totalSupply += sources[i].getSupply();

        int totalDemand = 0;
        for (int i = 0; i < destinationNumber; i++) totalDemand += destinations[i].getDemand();

        // Check if the supply is less than the demand.
        if (totalSupply < totalDemand) {
            System.out.println("Problem cannot be solved! The demand is bigger than the supply!");
            return;
        }

        while (totalDemand > 0) {
            minimumCost = Integer.MAX_VALUE;
            source = dest = quantity = 0;

            for (int i = 0; i < sourceNumber; i++) {
                for (int j = 0; j < destinationNumber; j++) {
                    if (sources[i].getSupply() > 0 && destinations[j].getDemand() > 0) {
                        if (costs[i][j] == minimumCost) {
                            if (Math.min(sources[i].getSupply(), destinations[j].getDemand()) > quantity) {
                                source = i;
                                dest = j;
                                minimumCost = costs[i][j];
                                quantity = Math.min(sources[i].getSupply(), destinations[j].getDemand());
                            }
                        } else if (costs[i][j] < minimumCost) {
                            source = i;
                            dest = j;
                            minimumCost = costs[i][j];
                            quantity = Math.min(sources[i].getSupply(), destinations[j].getDemand());
                        }
                    }
                }
            }
            solutionBuilder.append(sources[source].getName()).
                    append(" -> ").
                    append(destinations[dest].getName()).
                    append(": ").append(quantity).
                    append(" units * cost ").
                    append(costs[source][dest]).
                    append(" = ").
                    append(quantity * costs[source][dest]).
                    append("\n");

            totalDemand -= quantity;
            finalCost += (quantity * costs[source][dest]);

            sources[source].setSupply(sources[source].getSupply() - quantity);
            destinations[dest].setDemand(destinations[dest].getDemand() - quantity);
        }

        solution = new Solution(finalCost, solutionBuilder.toString());
    }

    /**
     * Display the instance of the problem the way it is
     * displayed in the example from the laboratory.
     */
    @Override
    public String toString() {


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
        for (int i = 0; i < destinationNumber; i++) {
            lines.append(String.format("%4s|", destinations[i].getDemand()));
        }
        lines.append("\n");

        return lines.toString();
    }

}
