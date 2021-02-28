package com.cezartodirisca;

public interface Visitable extends Comparable{
    void setStartTime(int newStartTime);
    void setCloseTime(int newCloseTime);
    int getStartTime();
    int getCloseTime();


}
