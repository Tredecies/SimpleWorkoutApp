package com.example.workout;

import android.os.SystemClock;

import java.util.Locale;

public class StopWatch {
    private long StartTime;

    public long resetTime() {
        StartTime = SystemClock.uptimeMillis();
        return StartTime;
    }

    public String getCurrentTime(){
        return getCurrentTime("%02d:%02d:%03d");
    }

    public String getCurrentTime(String format){
        long MillisecondTime = SystemClock.uptimeMillis() - StartTime;
        long Seconds = (int) (MillisecondTime / 1000);
        long Minutes = Seconds / 60;
        Seconds = Seconds % 60;
        long MilliSeconds = (int) (MillisecondTime % 1000);

        String result = String.format(
                Locale.getDefault(),
                format, Minutes, Seconds, MilliSeconds);
        return result;
    }


}
