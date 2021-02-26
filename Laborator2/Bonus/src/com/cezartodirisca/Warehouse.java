package com.cezartodirisca;

public class Warehouse extends Source{
    /**
     *
     * @param newName the name of the warehouse
     * @param newSupply the number of units the warehouse can supply
     */
    public Warehouse(String newName, int newSupply) {
        this.name = newName;
        this.supply = newSupply;
    }

    @Override
    public String toString()
    {
        return "This is a warehouse with the name : " + name + ".";
    }

}
