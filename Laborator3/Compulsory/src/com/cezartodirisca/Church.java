package com.cezartodirisca;

public class Church extends Location implements Visitable {
    private int startTime, closeTime;

    public Church(String newName, int startTime, int closeTime) {
        super(newName);
        this.startTime = startTime;
        this.closeTime = closeTime;
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
