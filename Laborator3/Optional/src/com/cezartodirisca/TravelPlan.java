package com.cezartodirisca;

public class TravelPlan {
    private final City cityToVisit;
    int[] visitOrder;

    /**
     *
     * @param chosenCity the city that holds the locations to be visited
     * @param visitOrder the preferences of the tourist
     */
    public TravelPlan(City chosenCity,int[] visitOrder)
    {
        this.cityToVisit = chosenCity;
        this.visitOrder = visitOrder.clone();
    }
}
