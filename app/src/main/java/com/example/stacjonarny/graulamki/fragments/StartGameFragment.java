package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.DifficultyLevelAdapter;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

public class StartGameFragment extends Fragment {

    private DifficultLevel[] difficultLevels;
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoadDifficultLevels();
        DifficultyLevelAdapter adapter = new DifficultyLevelAdapter(getActivity(), difficultLevels);
        list = (ListView) getActivity().findViewById(R.id.difficultyLevels);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListHandler());
    }


    // load difficult levels from string array resources
    public void LoadDifficultLevels()
    {
        difficultLevels = new DifficultLevel[5];
        String [] tab = getResources().getStringArray(R.array.difficulty_levels);
        for(int i = 0; i < difficultLevels.length; i++)
        {
            String[] temp = tab[i].split(",");
            difficultLevels[i] = new DifficultLevel(temp[0], Float.valueOf(temp[2]),Integer.valueOf(temp[1]), Integer.parseInt(temp[3]) ,temp[4]);
        }
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
            transaction.replace(R.id.fragment_container, play_mode_fragment);
            transaction.addToBackStack("test");
            transaction.commit();
            play_mode_fragment.CreateGame();
        }
    }
}
