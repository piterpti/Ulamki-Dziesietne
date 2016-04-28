package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutGameFragment extends Fragment {
    private int mMorphCounter1 = 1;
    private int mMorphCounter2 = 1;

    public AboutGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_game, container, false);
        return view;
    }

}
