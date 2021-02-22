package com.cezartodirisca;


public abstract class Source {
    protected String name;
    protected int supply;

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getSupply() {
        return this.supply;
    }

    public void setSupply(int newSupply) {
        this.supply = newSupply;
    }

    // Override the equals method
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)return false;
        if(!(obj instanceof  Source))return false;

        Source temp = (Source) obj;
        return temp.name.equals(this.name);
    }

    // Override the toString method to display the type of the source and its name
    @Override
    public abstract String toString() ;
}
