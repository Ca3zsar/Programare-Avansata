package com.cezartodirisca;

public class Restaurant extends Location implements Classifiable, Visitable {
    private int rank;
    private int startTime, closeTime;

    public Restaurant(String newName, int newRank, int startTime, int closeTime) {
        super(newName);
        this.rank = newRank;
        this.startTime = startTime;
        this.closeTime = closeTime;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int newRank) {
        this.rank = newRank;
    }

    @Override
    public int getStartTime() {
        return this.startTime;
    }

    @Override
    public void setStartTime(int newStartTime) {
        this.startTime = newStartTime;
    }

    @Override
    public int getCloseTime() {
        return this.closeTime;
    }

    @Override
    public void setCloseTime(int newCloseTime) {
        this.closeTime = newCloseTime;
    }
}
