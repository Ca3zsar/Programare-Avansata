package com.cezartodirisca;

public class Destination {
    private String name;
    private int demand;

    // Initialize the Destination using Constructor
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

    // Override the equals method
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)return false;
        if(!(obj instanceof  Destination))return false;

        Destination temp = (Destination) obj;
        return temp.name.equals(this.name);
    }

    // Use of the toString method to display the name of the destination.
    @Override
    public String toString() {
        return "The name of the destination is " + this.name + ".";
    }
}
