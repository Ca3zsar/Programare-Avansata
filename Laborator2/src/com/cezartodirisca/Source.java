package com.cezartodirisca;

public class Source {
    private SourceType type;
    private String name;
    private int supply;

    public Source(SourceType newType, String newName, int newSupply) {
        this.type = newType;
        this.name = newName;
        this.supply = newSupply;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public SourceType getType() {
        return this.type;
    }

    public void setType(SourceType newType) {
        this.type = newType;
    }

    public int getSupply() {
        return this.supply;
    }

    public void setSupply(int newSupply) {
        this.supply = newSupply;
    }

    @Override
    public String toString() {
        String printType;
        switch (this.type) {
            case FACTORY:
                printType = "Factory";
            case WAREHOUSE:
                printType = "Warehouse";
            default:
                printType = "Construction";
        }
        return "This is a " + printType + " with the name : " + name + ".";
    }
}
