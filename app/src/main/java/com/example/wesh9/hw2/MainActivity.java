package com.example.wesh9.hw2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Control_Frag.OnFragmentInteractionListener, List_Frag.OnFragmentInteractionListener {
    private static final String CONTROL_TAG = "control_Tag";
    private static final String LIST_TAG = "list_Tag";

    Button change;
    List_Frag list_frag;
    Control_Frag control_frag;
    int startFrag;
    LinearLayout fragment_container;
    int lap;
    int [] allTimes;
    int running;
    int pauseCall;
    int hour;
    int seconds;
    int minute;
    TimeAsyncTask timeAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lap = 1;
        timeAsyncTask = new TimeAsyncTask();

        allTimes = new int[3];
        control_frag = new Control_Frag();
        list_frag = new List_Frag();
        control_frag.setOrientation(1);
        setContentView(R.layout.activity_main);
        fragment_container = (LinearLayout) findViewById(R.id.fragment_container);
        change = (Button) findViewById(R.id.change);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            change.setOnClickListener(this);

        Intent intent = getIntent();
        running = intent.getIntExtra("running", 0);
        seconds = intent.getIntExtra("seconds", 0);
        minute = intent.getIntExtra("minute", 0);
        hour = intent.getIntExtra("hour", 0);

        String[] mystrings = new String[5];
        mystrings[0] = getIntent().getStringExtra("lap1");
        mystrings[1] = getIntent().getStringExtra("lap2");
        mystrings[2] = getIntent().getStringExtra("lap3");
        mystrings[3] = getIntent().getStringExtra("lap4");
        mystrings[4] = getIntent().getStringExtra("lap5");
        list_frag.setLaps(mystrings);
        lap = getIntent().getIntExtra("lapNum", 1);
        control_frag.setIsRunning(running);





        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, control_frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, list_frag).commit();

        if(running == 1){
            timeAsyncTask = new TimeAsyncTask();
            timeAsyncTask.execute();
        }

        startFrag = 0;
        pauseCall = 0;


    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(v.getId() == change.getId() && startFrag == 0) {
            transaction.replace(R.id.fragment_container, list_frag);
            transaction.addToBackStack(null);
            transaction.commit();
            change.setText("<<");
            change.setX(40);
            startFrag = 1;
        }
        else if(v.getId() == change.getId() && startFrag == 1){
            transaction.replace(R.id.fragment_container, control_frag);
            transaction.addToBackStack(null);
            transaction.commit();
            change.setText(">>");
            change.setX(750);
            startFrag = 0;
        }


    }

    public void stop(){
        timeAsyncTask.cancel(true);
        control_frag.setIsRunning(0);
    }

    public void reset(){
        seconds = 0;
        minute = 0;
        hour = 0;
        lap = 1;
        list_frag.resetLaps();
        control_frag.setIsRunning(0);

        timeAsyncTask.cancel(true);
    }
    public void getandsetTime(String time){
        String times = time;
        list_frag.setTimes(times, lap);
        lap++;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onPause() {
        timeAsyncTask.cancel(true);
        super.onPause();
    }

    public void exec(){
        if(timeAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            timeAsyncTask = new TimeAsyncTask();
            timeAsyncTask.execute();
            control_frag.setIsRunning(1);
        }
    }

    @Override
    protected void onDestroy() {


        Intent intent = new Intent(this, DualPane_Activity.class);
        intent.putExtra("seconds", seconds);
        intent.putExtra("minute", minute);
        intent.putExtra("hour", hour);
        String[] strings = list_frag.getLaps();
        intent.putExtra( "lap1",strings[0]);
        intent.putExtra( "lap2",strings[1]);
        intent.putExtra( "lap3",strings[2]);
        intent.putExtra( "lap4",strings[3]);
        intent.putExtra( "lap5",strings[4]);
        intent.putExtra("lapNum", lap);

        if(timeAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            intent.putExtra("running", 1);
        }
        else{
            intent.putExtra("running", 0);
        }
        timeAsyncTask.cancel(true);
        startActivity(intent);
        super.onDestroy();
    }

    private class TimeAsyncTask extends AsyncTask<Void, String, Void> {


        @Override
        protected Void doInBackground(Void... params) {



            while (!isCancelled()) {


                String time = String.format("%02d", hour) + ":"+ String.format("%02d", minute) + ":"
                        + String.format("%02d", seconds);
                try {

                    this.publishProgress(time);
                    Thread.sleep(1000);
                    seconds++;
                    if(seconds == 60){
                        seconds = 0;
                        minute++;
                    }
                    if(minute == 60){
                        minute = 0;
                        seconds= 0;
                        hour++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if(isCancelled()){
                    break;
                }
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            int[] times = new int[3];
            times[0] = seconds;
            times[1] = minute;
            times[2] = hour;
            control_frag.setCurrTime(times, 1);


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}



