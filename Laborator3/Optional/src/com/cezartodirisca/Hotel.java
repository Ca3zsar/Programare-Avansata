package com.cezartodirisca;

public class Hotel extends Location implements Classifiable{
    private int rank;

    public Hotel(String newName, int newRank)
    {
        super(newName);
        this.rank = newRank;
    }

    @Override
    public void setRank(int newRank)
    {
        this.rank = newRank;
    }

    @Override
    public int getRank()
    {
        return rank;
    }

}
