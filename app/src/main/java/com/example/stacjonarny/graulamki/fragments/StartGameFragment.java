package com.example.stacjonarny.graulamki.fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stacjonarny.graulamki.DifficultyLevelAdapter;
import com.example.stacjonarny.graulamki.R;


public class StartGameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DifficultyLevelAdapter adapter = new DifficultyLevelAdapter(getActivity(), getResources().getStringArray(R.array.difficulty_levels));
        ListView list = (ListView) getActivity().findViewById(R.id.difficultyLevels);
        list.setAdapter(adapter);

    }
}
