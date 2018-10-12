package com.example.wesh9.hw2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link List_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class List_Frag extends Fragment {

    private OnFragmentInteractionListener mListener;

    public List_Frag() {
        // Required empty public constructor
    }

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    String l1 = "1: -";
    String l2 = "2: -";
    String l3 ="3: -";
    String l4 ="4: -";
    String l5 = "5: -";
    String [] timeArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_, container, false);
        t1 = (TextView)view.findViewById(R.id.lap1);
        t1.setText(l1);
        t2 = (TextView)view.findViewById(R.id.lap2);
        t2.setText(l2);
        t3 = (TextView)view.findViewById(R.id.lap3);
        t3.setText(l3);
        t4 = (TextView)view.findViewById(R.id.lap4);
        t4.setText(l4);
        t5 = (TextView)view.findViewById(R.id.lap5);
        t5.setText(l5);

        return view;
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

    public void setTimes(String time, int lap){
        if(lap == 1) {
            l1 ="1: " + time;
            t1.setText(l1);
        }
        if(lap == 2) {
            l2 ="2: " + time;
            t2.setText(l2);
        }
        if(lap == 3) {
            l3 ="3: " + time;
            t3.setText(l3);
        }
        if(lap == 4){
            l4 ="4: " + time;
            t4.setText(l4);
        }
        if(lap == 5) {
            l5 ="5: " + time;
            t5.setText(l5);
        }

    }
    public String[] getLaps(){
        String[] strings = new String[5];
        strings[0] = l1;
        strings[1] = l2;
        strings[2] = l3;
        strings[3] = l4;
        strings[4] = l5;
        return strings;
    }

    public void setLaps(String[] strings){
        l1 = strings[0];
        l2 = strings[1];
        l3 = strings[2];
        l4 = strings[3];
        l5 = strings[4];
    }

    public void resetLaps(){
         l1 = "1: -";
         l2 = "2: -";
         l3 ="3: -";
         l4 ="4: -";
         l5 = "5: -";
        t1.setText(l1);
        t2.setText(l2);
        t3.setText(l3);
        t4.setText(l4);
        t5.setText(l5);
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
