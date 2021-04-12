package com.cezartodirisca;

import java.time.LocalTime;

public class Restaurant extends Location implements Classifiable,Visitable{
    private int rank;
    private LocalTime startTime, closeTime;

    public Restaurant(String newName, int newRank, LocalTime startTime, LocalTime closeTime)
    {
        super(newName);
        this.rank = newRank;
        this.startTime = startTime;
        this.closeTime = closeTime;
    }

    public Restaurant(String newName,int newRank)
    {
        super(newName);
        this.rank = newRank;
        setDefaultHours();
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

    @Override
    public void setStartTime(LocalTime newStartTime) {
        this.startTime = newStartTime;
    }

    @Override
    public void setCloseTime(LocalTime newCloseTime) {
        this.closeTime = newCloseTime;
    }

    @Override
    public LocalTime getStartTime() {
        return this.startTime;
    }

    @Override
    public LocalTime getCloseTime() {
        return this.closeTime;
    }
}
