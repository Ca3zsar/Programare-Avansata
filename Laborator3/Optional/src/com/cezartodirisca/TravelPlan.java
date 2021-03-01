package com.cezartodirisca;

import java.util.*;

public class TravelPlan {
    private final City cityToVisit;
    private final ArrayList<Integer> visitOrder;
    private final Map<String, Map<String,Integer>>costs;

    /**
     *
     * @param chosenCity the city that holds the locations to be visited
     * @param visitOrder the preferences of the tourist
     */
    public TravelPlan(City chosenCity,ArrayList<Integer> visitOrder)
    {
        this.cityToVisit = chosenCity;
        this.visitOrder = visitOrder;

        this.costs = new HashMap<>();
        for(Location location:cityToVisit.getLocationList())
        {
            this.costs.put(location.getName(),location.getAllDistances());
        }
    }

    /**
     * This function implements Dijkstra Algorithm, starting from the firstLocation
     * and calculating the minimum to all the locations from the current city. In the end
     * it will print the minimum cost path from the firstLocation to secondLocation.
     * @param firstLocation The location from which is considered the starting point.
     * @param secondLocation The final location
     * @return the minimum cost of going from firstLocation to secondLocation
     */
    public int computeShortestPath(Location firstLocation, Location secondLocation)
    {
        Map<String,Integer> distances = new HashMap<>(); // the distances from the firstLocation to all the other locations.
        Map<String,String> parents = new HashMap<>(); // the previous location in the path
        Queue<String> queue = new LinkedList<>(); // a queue of the remaining location to compute minimum cost for

        for (Location location: cityToVisit.getLocationList()) { // Initialize all the distances to Integer.MAX_VALUE and add them to queue
            distances.put(location.getName(), Integer.MAX_VALUE);
            queue.add(location.getName());
        }

        distances.put(firstLocation.getName(),0);
        parents.put(firstLocation.getName(),firstLocation.getName());

        while(!queue.isEmpty())
        {
            int minimum = Integer.MAX_VALUE;
            String toChoose = "";

            for(String locationName:queue) // Look for the location with the minimum cost to go to.
            {
                if(distances.get(locationName) <= minimum)
                {
                    minimum = distances.get(locationName);
                    toChoose = locationName;
                }
            }

            queue.remove(toChoose); // Remove it from the queue.

            for(Map.Entry<String,Integer> entry : costs.get(toChoose).entrySet()) //Iterate through the neighbours
            {
                if(queue.contains(entry.getKey())) // Check if the neighbour is in the queue
                {
                    int temporaryCost = distances.get(toChoose) + costs.get(toChoose).get(entry.getKey());
                    if(temporaryCost < distances.get(entry.getKey())) // Compare the current cost and the potentially better cost.
                    {
                        distances.put(entry.getKey(),temporaryCost);
                        parents.put(entry.getKey(), toChoose);
                    }
                }
            }
        }

        String currentLocation = secondLocation.getName();
        StringBuilder path = new StringBuilder();

        while(!currentLocation.equals(parents.get(currentLocation))) // Reconstruct the solution.
        {
            StringBuilder temporaryToAdd = new StringBuilder();
            temporaryToAdd.append(currentLocation);

            path.append(temporaryToAdd.reverse()).append(" >- ");
            currentLocation = parents.get(currentLocation);
        }

        StringBuilder temporaryToAdd = new StringBuilder();
        temporaryToAdd.append(firstLocation.getName());

        path.append(temporaryToAdd.reverse());

        path = path.reverse();
        System.out.println(path.toString());

        return distances.get(secondLocation.getName());
    }
}
