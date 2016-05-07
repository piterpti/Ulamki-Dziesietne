package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.DifficultyLevelAdapter;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

public class StartGameFragment extends Fragment {


    public static final String GAME_FRAGMENT_TAG = "GAME";
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DifficultyLevelAdapter adapter = new DifficultyLevelAdapter(getActivity(), MainActivity.difficultLevels);
        list = (ListView) getActivity().findViewById(R.id.difficultyLevels);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListHandler());
    }

    // class to handle difficulty levels list
    public class ListHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle args = new Bundle();
            MainActivity.gameDifficultLevel = (DifficultLevel) list.getItemAtPosition(position);
            Game play_mode_fragment = new Game();
            play_mode_fragment.setArguments(args);
            play_mode_fragment.setRetainInstance(true);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, play_mode_fragment,GAME_FRAGMENT_TAG);
            transaction.commit();
            play_mode_fragment.CreateGame();
        }
    }

}
