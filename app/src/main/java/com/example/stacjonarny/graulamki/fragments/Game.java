package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game extends Fragment {
    public TextView time_remain;
    public Button go_back_button;
    public Game() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.fragment_game, container, false);
            time_remain = (TextView) view.findViewById(R.id.time_remain);
        //button
            go_back_button = (Button) view.findViewById(R.id.go_back_to_difficulty_levels);
            go_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu main_menu_fragment = new MainMenu();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, main_menu_fragment);
                transaction.commit();
            }
        });
            //Timer
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    time_remain.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    time_remain.setText("time end!");
                }
            }.start();

            return view;

        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
