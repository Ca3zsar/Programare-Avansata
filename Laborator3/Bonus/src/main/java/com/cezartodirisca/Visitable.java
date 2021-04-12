package com.cezartodirisca;

import java.time.Duration;
import java.time.LocalTime;

public interface Visitable extends Comparable {
    static Duration getVisitingHours(Visitable location) {
        LocalTime startHour = location.getStartTime();
        LocalTime closeHour = location.getCloseTime();
        return Duration.between(closeHour, startHour);
    }

    default LocalTime getStartTime() {
        return LocalTime.of(8,30);
    }

    void setStartTime(LocalTime newStartTime);

    default LocalTime getCloseTime()
    {
        return LocalTime.of(20,0);
    }

    void setCloseTime(LocalTime newCloseTime);

    default void setDefaultHours() {
        setStartTime(LocalTime.of(9, 30));
        setCloseTime(LocalTime.of(20, 0));
    }
}
