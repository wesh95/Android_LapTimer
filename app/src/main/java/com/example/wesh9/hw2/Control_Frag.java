package com.example.wesh9.hw2;

import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Control_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Control_Frag extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    TextView timer;
    Button start;
    Button lap;
    Button reset;
    //TimeAsyncTask timeAsyncTask;
    int seconds;
    int minute;
    int hour;
    int exec;
    String lapString;
    String currTime = "00:00:00";
    int orientation;
    int isRunning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Control_Frag() {
        // Required empty public constructor
    }

    public void setIsRunning(int is){
        isRunning = is;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_, container, false);
        timer = (TextView)view.findViewById(R.id.currTime);
        timer.setText(String.format("%02d", hour) + ":"+ String.format("%02d", minute) + ":"
                + String.format("%02d", seconds));
        start = (Button)view.findViewById(R.id.start);
        if(isRunning == 1){
            start.setText("stop");
        }
        else{
            start.setText("start");
        }

        lap = (Button)view.findViewById(R.id.lap);
        reset = (Button)view.findViewById(R.id.reset);
        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);
        if(exec == 1){
            //timeAsyncTask.execute();
        }
//        if(timeAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
//            start.setText("Pause");
//        }
        return view ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public String getText(){
        return lapString;
    }
    public void cancel(){
        //timeAsyncTask.cancel(true);
    }
    public void setOrientation(int orien){
        orientation = orien;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == start.getId()) {
            if (isRunning == 0) {//not running
                if (orientation == 1) { //in portrait
                    MainActivity activity = (MainActivity) getActivity();
                    activity.exec();
                    start.setText("stop");

                }
                if (orientation == 0) {//landscape
                    DualPane_Activity activity = (DualPane_Activity) getActivity();
                    activity.exec();
                    start.setText("stop");
                }
            } else {
                if (orientation == 1) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.stop();
                    start.setText("start");


                }
                if (orientation == 0) {
                    DualPane_Activity activity = (DualPane_Activity) getActivity();
                    activity.stop();
                    start.setText("start");

                }
//
            }
        }
            if (v.getId() == lap.getId()) {
                if (orientation == 0) {
                    DualPane_Activity activity = (DualPane_Activity) getActivity();
                    activity.getandSetTime(timer.getText().toString());
                } else if (orientation == 1) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.getandsetTime(timer.getText().toString());
                }
            }
            if (v.getId() == reset.getId()) {
                // timeAsyncTask.cancel(true);
                start.setText("start");
                timer.setText("00:00:00");
                if (orientation == 1) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.reset();
                } else {
                    DualPane_Activity activity = (DualPane_Activity) getActivity();
                    activity.reset();
                }
            }

    }

    public void stop(){
        start.setText("start");
    }

    public int[] getTimes(){
        int[] times = new int[3];
        times[0] = seconds;
        times[1] = minute;
        times[2] = hour;
        return times;
    }
    public void setCurrTime(int[] times, int running){
        seconds = times[0];
        minute = times[1];
        hour = times[2];
        timer.setText(String.format("%02d", hour) + ":"+ String.format("%02d", minute) + ":"
                + String.format("%02d", seconds));
        if(running == 1){
           exec = 1;
        }



    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

