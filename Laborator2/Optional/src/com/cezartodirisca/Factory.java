package com.cezartodirisca;

public class Factory extends Source{
    /**
     *
     * @param newName the name of the factory
     * @param newSupply the quantity of units the factory can supply
     */
    public Factory(String newName, int newSupply) {
        this.name = newName;
        this.supply = newSupply;
    }

    @Override
    public String toString()
    {
        return "This is a factory with the name : " + name + ".";
    }
}
