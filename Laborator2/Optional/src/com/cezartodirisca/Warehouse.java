package com.cezartodirisca;

public class Warehouse extends Source{
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
