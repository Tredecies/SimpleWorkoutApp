package com.example.workout;

import android.os.SystemClock;

public class StopWatch {
    private long StartTime = 0;

    public long resetTime() {
        StartTime = SystemClock.uptimeMillis();
        return StartTime;
    }
}
