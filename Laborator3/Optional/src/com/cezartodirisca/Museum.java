package com.cezartodirisca;

public class Museum extends Location implements Visitable,Payable{
    private int startTime, closeTime;
    private int entryFee;

    public Museum(String newName,int startTime, int closeTime,int entryFee)
    {
        super(newName);
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.entryFee = entryFee;
    }

    @Override
    public void setStartTime(int newStartTime) {
        this.startTime = newStartTime;
    }

    @Override
    public void setCloseTime(int newCloseTime) {
        this.closeTime = newCloseTime;
    }

    @Override
    public int getStartTime() {
        return this.startTime;
    }

    @Override
    public int getCloseTime() {
        return this.closeTime;
    }

    @Override
    public void setEntryFee(int newEntryFee) {
        this.entryFee = newEntryFee;
    }

    @Override
    public int getEntryFee() {
        return this.entryFee;
    }
}
