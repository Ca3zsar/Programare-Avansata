package com.cezartodirisca;

import java.time.Duration;
import java.time.LocalTime;

public interface Visitable extends Comparable{
    void setStartTime(LocalTime newStartTime);
    void setCloseTime(LocalTime newCloseTime);
    LocalTime getStartTime();
    LocalTime getCloseTime();

    default void setDefaultHours()
    {
        setStartTime(LocalTime.of(9,30));
        setCloseTime(LocalTime.of(20,0));
    }

    static Duration getVisitingHours(Visitable location)
    {
        LocalTime startHour = location.getStartTime();
        LocalTime closeHour = location.getCloseTime();
        return Duration.between(closeHour,startHour);
    }
}
