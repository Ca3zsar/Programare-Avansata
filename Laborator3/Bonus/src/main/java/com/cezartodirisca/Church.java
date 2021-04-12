package com.cezartodirisca;

import java.time.LocalTime;

public class Church extends Location implements Visitable{
    private LocalTime startTime,closeTime;
    public Church(String newName,LocalTime startTime,LocalTime closeTime)
    {
        super(newName);
        this.startTime = startTime;
        this.closeTime = closeTime;
    }

    public Church(String newName)
    {
        super(newName);
        setDefaultHours();
    }

    @Override
    public void setStartTime(LocalTime newStartTime)
    {
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
