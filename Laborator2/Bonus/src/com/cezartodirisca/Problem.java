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
     * To solve the problem in a better way an heuristic algorithm will be used, namely
     * Vogel's approximation method.
     * This method implies finding the least two costs from every source and every destination
     * and determine "the penalty" as the absolute difference between the two costs.
     * Then, the maximum penalty will be chosen. The minimum between the supply and the demand
     * on that cell will be depleted, and the source/destination will no longer be used.
     * Repeat the first steps until the supply is depleted or the demand satisfied.
     */
    public void solveProblem() {
        // The problem can be solved only once because the solver will alter the data from the problem.
        if (solution != null) {
            System.out.println("Problem already solved! Try using 'getSolution'.");
            return;
        }

        int minimumCost;
        int secondMinimumCost;

        int []rowDone = new int[sourceNumber];
        int []columnDone = new int[destinationNumber];

        int notDoneRows = sourceNumber;
        int notDoneColumns = destinationNumber;

        int finalCost = 0;
        int quantity;
        int source, destination;

        StringBuilder solutionBuilder = new StringBuilder("");

        // Take the case where there is only one source or destination.

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
            source = destination = 0;

            int[] sourcePenalties = new int[sourceNumber];
            int[] destinationPenalties = new int[destinationNumber];

            if(notDoneColumns == 1)
            {
                while(totalDemand>0)
                {
                    quantity = 0;
                    minimumCost = Integer.MAX_VALUE;
                    for(int i=0;i<destinationNumber;i++)
                    {
                        if(columnDone[i] == 0)
                        {
                            destination = i;
                            break;
                        }
                    }
                    for(int i=0;i<sourceNumber;i++)
                    {
                        if(rowDone[i] == 0)
                        {
                            if(costs[i][destination] < minimumCost)
                            {
                                minimumCost = costs[i][destination];
                                source = i;
                            }
                        }
                    }

                    quantity = Math.min(sources[source].getSupply(),destinations[destination].getDemand());

                    solutionBuilder.append(sources[source].getName()).
                            append(" -> ").
                            append(destinations[destination].getName()).
                            append(": ").append(quantity).
                            append(" units * cost ").
                            append(costs[source][destination]).
                            append(" = ").
                            append(quantity * costs[source][destination]).
                            append("\n");

                    totalDemand -= quantity;
                    finalCost += (quantity * costs[source][destination]);

                    sources[source].setSupply(sources[source].getSupply() - quantity);
                    rowDone[source] = 1;
                    destinations[destination].setDemand(destinations[destination].getDemand() - quantity);
                }
                break;
            }

            for (int i = 0; i < sourceNumber; i++) {
                minimumCost = Integer.MAX_VALUE;
                secondMinimumCost = Integer.MAX_VALUE;

                if (rowDone[i] == 0) {
                    for (int j = 0; j < destinationNumber; j++) {
                        if (columnDone[j] == 0) {
                            if (costs[i][j] <= minimumCost) {
                                secondMinimumCost = minimumCost;
                                minimumCost = costs[i][j];
                            } else {
                                if (costs[i][j] <= secondMinimumCost) {
                                    secondMinimumCost = costs[i][j];
                                }
                            }
                        }
                    }
                    if(secondMinimumCost == Integer.MAX_VALUE)
                    {
                        secondMinimumCost=minimumCost;
                    }
                    sourcePenalties[i] = Math.abs(secondMinimumCost - minimumCost);
                }
            }
            for (int i = 0; i < destinationNumber; i++) {
                minimumCost = Integer.MAX_VALUE;
                secondMinimumCost = Integer.MAX_VALUE;

                if(columnDone[i] == 0) {
                    for (int j = 0; j < sourceNumber; j++) {
                        if (rowDone[j] == 0) {
                            if(costs[j][i] <= minimumCost) {
                                secondMinimumCost = minimumCost;
                                minimumCost = costs[j][i];
                            }else{
                                if(costs[j][i] <= secondMinimumCost)
                                {
                                    secondMinimumCost = costs[j][i];
                                }
                            }
                        }
                    }
                    destinationPenalties[i] = Math.abs(secondMinimumCost-minimumCost);
                }
            }

            String typeOfLine = null;
            int index=0;
            int maxPenalty = 0;
            for(int i=0;i<sourceNumber;i++)
            {
                if(rowDone[i] == 0 && sourcePenalties[i] >= maxPenalty)
                {
                    maxPenalty = sourcePenalties[i];
                    index = i;
                    typeOfLine = "r";
                }
            }
            for(int i=0;i<destinationNumber;i++)
            {
                if(columnDone[i] == 0 && destinationPenalties[i] >= maxPenalty)
                {
                    maxPenalty = destinationPenalties[i];
                    index = i;
                    typeOfLine = "c";
                }
            }

            if(typeOfLine.equals("r"))
            {
                minimumCost = Integer.MAX_VALUE;
                for(int i=0;i<destinationNumber;i++)
                {
                    if(columnDone[i] == 0 && costs[index][i] < minimumCost)
                    {
                        minimumCost = costs[index][i];
                        destination = i;
                    }
                }
                source = index;
            }else{
                minimumCost = Integer.MAX_VALUE;
                for(int i=0;i<sourceNumber;i++)
                {
                    if(rowDone[i]==0 && costs[i][index] < minimumCost)
                    {
                        minimumCost = costs[i][index];
                        source = i;
                    }
                }
                destination = index;
            }
            quantity = Math.min(sources[source].getSupply(),destinations[destination].getDemand());

            solutionBuilder.append(sources[source].getName()).
                    append(" -> ").
                    append(destinations[destination].getName()).
                    append(": ").append(quantity).
                    append(" units * cost ").
                    append(costs[source][destination]).
                    append(" = ").
                    append(quantity * costs[source][destination]).
                    append("\n");

            totalDemand -= quantity;
            totalSupply -= quantity;
            finalCost += (quantity * costs[source][destination]);

            sources[source].setSupply(sources[source].getSupply() - quantity);
            destinations[destination].setDemand(destinations[destination].getDemand() - quantity);

            if(sources[source].getSupply()==0)
            {
                notDoneRows--;
                rowDone[source] = 1;
            }
            if(destinations[destination].getDemand() == 0)
            {
                notDoneColumns--;
                columnDone[destination] = 1;
            }
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
