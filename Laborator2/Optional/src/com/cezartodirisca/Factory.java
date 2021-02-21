package com.cezartodirisca;

public class Factory extends Source{
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
