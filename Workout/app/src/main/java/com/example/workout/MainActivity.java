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

    private final String SNAPSHOT_TIME_FORMAT = "%02d:%02d";
    Button reset, restartButton, setButton;
    TextView setTextView, prevTextView, timerView;
    Handler handler = new Handler();
    int Set=0;
    Boolean running = false;
    List<String> prevTimes = new ArrayList<String>();
    List<Integer> sets = new ArrayList<Integer>();
    private StopWatch stopWatch = new StopWatch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initButtons();

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set = 0;
                sets.clear();
                stopWatch.resetTime();
                updateSetTextView();
                timerView.setText("00:00:00");
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
                stopWatch.resetTime();
                updatePrevTimesTextView();
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
                stopWatch.resetTime();
                handler.postDelayed(runnable, 0);
                updatePrevTimesTextView();
            }
        });
    }

    private void updateSetTextView(){
        setTextView.setText(Integer.toString(Set));
    }

    private void updatePrevTimesTextView(){
        String result = new String();
        Iterator<String> prevTimesIt = prevTimes.iterator();
        Iterator<Integer> setsIt = sets.iterator();
        while(prevTimesIt.hasNext() && setsIt.hasNext())
        for (Integer i=0; i<prevTimes.size(); i++){
            result += prevTimesIt.next() + " --- Set: " + setsIt.next() + "\n";
        }
        prevTextView.setText(result);
    }

    private void takeSnapShot(){
        prevTimes.add(stopWatch.getCurrentTime(SNAPSHOT_TIME_FORMAT));
        sets.add(Set);
    }

    public Runnable runnable = new Runnable() {

        public void run() {
            timerView.setText(stopWatch.getCurrentTime());
            handler.postDelayed(this, 0);
        }

    };

    private void initButtons(){
        reset = findViewById(R.id.reset);
        setButton = findViewById(R.id.setButton);
        restartButton = findViewById(R.id.restartButton);
    }

    private void initViews(){
        setTextView = (TextView)findViewById(R.id.setTextView);
        prevTextView = (TextView)findViewById(R.id.prevTimerTextView);
        timerView = (TextView)findViewById(R.id.timer);
    }
}