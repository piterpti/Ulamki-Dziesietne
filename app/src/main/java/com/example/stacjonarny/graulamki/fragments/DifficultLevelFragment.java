package com.example.stacjonarny.graulamki.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.DatabaseConnection;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.DifficultyLevelAdapter;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;
import java.util.*;

public class DifficultLevelFragment extends Fragment {

    public static final String GAME_FRAGMENT_TAG = "GAME";
    private ListView list;
    private ListView currentLevel;
    private DifficultyLevelAdapter currentAdapter;
    private DifficultLevel[] difficultLevels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DifficultLevel toAdd = null;
        difficultLevels = new DifficultLevel[4];
        int index = 0;
        for(DifficultLevel d : MainActivity.difficultLevels) {
            if(d.getLevelNum() == MainActivity.gameDifficultLevel.getLevelNum()) {
                toAdd = d;
            } else {
                difficultLevels[index++] = d;
            }
        }
        DifficultyLevelAdapter adapter = new DifficultyLevelAdapter(getActivity(), difficultLevels);
        list = (ListView) getActivity().findViewById(R.id.difficultyLevels);
        currentLevel = (ListView) getActivity().findViewById(R.id.currentLevel);


        currentAdapter = new DifficultyLevelAdapter(getActivity(), new DifficultLevel[]{toAdd});
        currentLevel.setAdapter(currentAdapter);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListHandler());
    }

    // class to handle difficulty levels list
    public class ListHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.gameDifficultLevel = (DifficultLevel) list.getItemAtPosition(position);
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MainActivity.DIFFICULT_LEVEL_KEY, MainActivity.gameDifficultLevel.getLevelNum() - 1);
            editor.commit();
            MainActivity.achievementList.clear();

            MainMenu main_menu_fragment = new MainMenu();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.slide_out,
                    R.anim.slide_in,
                    R.anim.slide_out);
            transaction.replace(R.id.fragment_container, main_menu_fragment);
            transaction.commit();
            new DatabaseConnection(MainActivity.mainContext);
        }
    }

}
