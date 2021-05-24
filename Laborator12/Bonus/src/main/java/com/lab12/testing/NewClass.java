package com.lab12.testing;

import java.sql.Time;

@TestClass
public class NewClass {
    @CorrectTest
    public static void main()
    {
        System.out.println("I am here");
    }

    @CorrectTest
    public int addition(int a,int b){
        return a + b;
    }

    @CorrectTest
    public String getMessage()
    {
        return null;
    }

    @SpeedTest
    public void timer()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
