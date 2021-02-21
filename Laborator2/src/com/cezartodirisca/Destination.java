package com.cezartodirisca;

public class Destination {
    private String name;
    private int demand;

    public Destination(String newName, int newDemand) {
        this.name = newName;
        this.demand = newDemand;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "The name of the destination is " + this.name + ".";
    }
}
