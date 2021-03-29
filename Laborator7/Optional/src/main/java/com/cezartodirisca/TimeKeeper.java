package com.cezartodirisca;

import java.util.concurrent.TimeUnit;

public class TimeKeeper extends Thread{
    public long timeElapsed;
    public boolean isRunning = true;
    public int elapsedSeconds=0;

    public void run()
    {
        long startTime = System.currentTimeMillis();

        while(isRunning && elapsedSeconds<=10)
        {
            try {
                TimeUnit.SECONDS.sleep(1);
                elapsedSeconds++;
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            timeElapsed = elapsedSeconds;
        }
    }
}
