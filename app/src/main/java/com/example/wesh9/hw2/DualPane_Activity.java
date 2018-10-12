package com.example.wesh9.hw2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

public class DualPane_Activity extends AppCompatActivity implements Control_Frag.OnFragmentInteractionListener,
List_Frag.OnFragmentInteractionListener{
    Control_Frag control_frag;
    List_Frag list_frag;
    int lap;
    int [] allTimes ;
    int seconds;
    int minute;
    int hour;
    TimeAsyncTask timeAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeAsyncTask = new TimeAsyncTask();
        lap = 1;

        control_frag = new Control_Frag();
        list_frag = new List_Frag();
        control_frag.setOrientation(0);
        setContentView(R.layout.activity_dual_pane_);

        Intent intent = getIntent();
        int running = intent.getIntExtra("running", 0);
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


        if(running == 1){
            timeAsyncTask = new TimeAsyncTask();
            timeAsyncTask.execute();
        }



        getSupportFragmentManager().beginTransaction().add(R.id.control_conatainer, control_frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.list_container, list_frag).commit();








    }
    public void exec(){
        if(timeAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            control_frag.setIsRunning(1);
            timeAsyncTask = new TimeAsyncTask();
            timeAsyncTask.execute();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void getandSetTime(String time){
        String times = time;
        list_frag.setTimes(times, lap);
        lap++;

    }
    public void stop(){
        timeAsyncTask.cancel(true);
        control_frag.setIsRunning(0);
    }

    @Override
    protected void onPause() {
        timeAsyncTask.cancel(true);
        super.onPause();
    }

    public void reset(){


        seconds = 0;
        minute = 0;
        hour = 0;
        lap = 1;
        control_frag.setIsRunning(0);
        list_frag.resetLaps();
        timeAsyncTask.cancel(true);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MainActivity.class);
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
            intent.putExtra( "laps",list_frag.getLaps());
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
