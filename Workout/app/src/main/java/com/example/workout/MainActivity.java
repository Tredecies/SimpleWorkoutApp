package com.example.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuItemHoverListener;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.strictmode.ContentUriWithoutPermissionViolation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timer, setTextView, prevTextView;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler = new Handler();
    int Seconds, Minutes, MilliSeconds, Set=0;
    Boolean running = false;
    List<String> prevTimes = new ArrayList<String>();
    List<Integer> sets = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTextView = (TextView)findViewById(R.id.setTextView);
        prevTextView = (TextView)findViewById(R.id.prevTimerTextView);
        timer = (TextView)findViewById(R.id.timer);

        Button reset = findViewById(R.id.reset);
        Button setButton = findViewById(R.id.setButton);
        Button restartButton = findViewById(R.id.restartButton);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set = 0;
                sets.clear();
                StartTime = SystemClock.uptimeMillis();
                updateSetTextView();
                timer.setText("00:00:00");
                handler.removeCallbacks(runnable);
                reset.setText("Start");
                prevTimes.clear();
                prevTextView.setText("");
                running = false;
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set++;
                updateSetTextView();;
                if(running != true){
                    return;
                }
                takeSnapShot();
                resetTime();
                updatePrevTimesText();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running == false){
                    running = true;
                } else {
                    takeSnapShot();
                }
                reset.setText("Reset");
                resetTime();
                handler.postDelayed(runnable, 0);
                updatePrevTimesText();
            }
        });
    }

    private void updateSetTextView(){
        setTextView.setText(Integer.toString(Set));
    }

    private void updatePrevTimesText(){
        String result = new String();
        Iterator<String> prevTimesIt = prevTimes.iterator();
        Iterator<Integer> setsIt = sets.iterator();
        while(prevTimesIt.hasNext() && setsIt.hasNext())
        for (Integer i=0; i<prevTimes.size(); i++){
            result += prevTimesIt.next() + " --- " + setsIt.next() + "\n";
        }
        prevTextView.setText(result);
    }

    private void takeSnapShot(){
        prevTimes.add(getCurrentTime());
        sets.add(Set);
    }

    private void resetTime(){
        StartTime = SystemClock.uptimeMillis();
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

    public Runnable runnable = new Runnable() {

        public void run() {
            timer.setText(getCurrentTime());
            handler.postDelayed(this, 0);
        }

    };
}