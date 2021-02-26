package com.cezartodirisca;

public class Solution {
    private final int totalCost;
    private final String solutionString;

    /**
     * @param newCost the final cost of the determined solution
     * @param solution the string representing the chosen routes
     */
    public Solution(int newCost, String solution)
    {
        this.totalCost = newCost;
        this.solutionString = solution;
    }

    @Override
    public String toString()
    {
        return solutionString + "Total cost : " + totalCost;
    }
}
