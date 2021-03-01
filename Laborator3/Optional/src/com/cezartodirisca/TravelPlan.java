package com.cezartodirisca;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TravelPlan {
    private final City cityToVisit;
    private final int[] visitOrder;
    private final Map<String, Map<String,Integer>>costs;

    /**
     *
     * @param chosenCity the city that holds the locations to be visited
     * @param visitOrder the preferences of the tourist
     */
    public TravelPlan(City chosenCity,int @NotNull [] visitOrder)
    {
        this.cityToVisit = chosenCity;
        this.visitOrder = visitOrder.clone();

        this.costs = new HashMap<>();
        for(Location location:cityToVisit.getLocationList())
        {
            this.costs.put(location.getName(),location.getAllDistances());
        }
    }

    public int computeShortestPath(Location firstLocation, Location secondLocation)
    {
        int numberOfLocations = costs.size();
        Map<String,Integer> distances = new HashMap<>();
        Map<String,String> parents = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        for (Location location: cityToVisit.getLocationList()) {
            distances.put(location.getName(), Integer.MAX_VALUE);
            queue.add(location.getName());
        }

        distances.put(firstLocation.getName(),0);
        parents.put(firstLocation.getName(),firstLocation.getName());

        while(!queue.isEmpty())
        {
            int minimum = Integer.MAX_VALUE;
            String toChoose = "";

            for(String locationName:queue)
            {
                if(distances.get(locationName) <= minimum)
                {
                    minimum = distances.get(locationName);
                    toChoose = locationName;
                }
            }

            queue.remove(toChoose);

            for(Map.Entry<String,Integer> entry : costs.get(toChoose).entrySet())
            {
                if(queue.contains(entry.getKey()))
                {
                    int temporaryCost = distances.get(toChoose) + costs.get(toChoose).get(entry.getKey());
                    if(temporaryCost < distances.get(entry.getKey()))
                    {
                        distances.put(entry.getKey(),temporaryCost);
                        parents.put(entry.getKey(), toChoose);
                    }
                }
            }
        }

        return distances.get(secondLocation.getName());
    }
}
