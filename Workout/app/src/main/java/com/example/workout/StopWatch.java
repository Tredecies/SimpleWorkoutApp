package com.example.workout;

import android.os.SystemClock;

import java.util.Locale;

public class StopWatch {
    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    private int Seconds, Minutes, MilliSeconds, Set=0;

    public long resetTime() {
        StartTime = SystemClock.uptimeMillis();
        return StartTime;
    }

    public String getCurrentTime(){
        MillisecondTime = SystemClock.uptimeMillis() - StartTime;

        UpdateTime = TimeBuff + MillisecondTime;

        Seconds = (int) (UpdateTime / 1000);

        Minutes = Seconds / 60;

        Seconds = Seconds % 60;

        MilliSeconds = (int) (UpdateTime % 1000);

        String result = String.format(
                Locale.getDefault(),
                "%02d:%02d:%03d", Minutes, Seconds, MilliSeconds);
        return result;
    }

}
