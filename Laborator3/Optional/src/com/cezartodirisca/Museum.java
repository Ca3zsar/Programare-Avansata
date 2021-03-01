package com.cezartodirisca;

import java.time.LocalTime;

public class Museum extends Location implements Visitable,Payable{
    private LocalTime startTime, closeTime;
    private int entryFee;

    public Museum(String newName,LocalTime startTime, LocalTime closeTime,int entryFee)
    {
        super(newName);
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.entryFee = entryFee;
    }

    public Museum(String newName,int entryFee)
    {
        super(newName);
        this.entryFee = entryFee;
        setDefaultHours();
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

    @Override
    public void setEntryFee(int newEntryFee) {
        this.entryFee = newEntryFee;
    }

    @Override
    public int getEntryFee() {
        return this.entryFee;
    }
}
