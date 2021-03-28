package com.cezartodirisca;

public class Token {
    private int value;
    private int xCoordinate;
    private int yCoordinate;

    public Token(int value, int xCoordinate, int yCoordinate)
    {
        this.value = value;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getValue() {
        return value;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }
}
