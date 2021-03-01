package com.cezartodirisca;

import java.util.HashMap;
import java.util.Map;

public abstract class Location implements Comparable {
    private String name;
    private Map<String, Integer> costs;

    public Location(String newName) {
        this.name = newName;
        costs = new HashMap<>();
    }

    /**
     * Compares a location with another location based on their name
     *
     * @param other The location to compare to.
     */
    @Override
    public int compareTo(Object other) {
        if (other == null)
            throw new NullPointerException();
        if (!(other instanceof Location))
            throw new ClassCastException("Incomparable objects!");
        Location newLocation = (Location) other;
        return this.name.compareTo(newLocation.name);
    }

    public String getName() {
        return this.name;
    }

    public void setDistance(String newLocation, Integer cost) {
        this.costs.put(newLocation, cost);
    }

    public Integer getDistance(String requiredLocation) {
        return this.costs.get(requiredLocation);
    }

    public Map<String, Integer> getAllDistances() {
        return this.costs;
    }
}
