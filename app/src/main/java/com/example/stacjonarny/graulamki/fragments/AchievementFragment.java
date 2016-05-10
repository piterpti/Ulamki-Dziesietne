package com.example.stacjonarny.graulamki.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.*;

import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.AchievementAdapter;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment implements AdapterView.OnItemLongClickListener {


    private ListView achievementList;
    private AchievementAdapter adapter;

    public AchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        achievementList = (ListView) view.findViewById(R.id.achievementMenuList);
        LoadAchievementsFromList();
        return view;
    }

    public void LoadAchievementsFromList()
    {
        achievementList.setAdapter(null);
        Collections.sort(MainActivity.achievementList);
        adapter = new AchievementAdapter(getActivity(), MainActivity.achievementList);
        achievementList.setAdapter(adapter);
        achievementList.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Achievement achievement = (Achievement) parent.getItemAtPosition(position);
        final AchievementAdapter a = (AchievementAdapter) parent.getAdapter();
        if( ! achievement.isLocked())
        {
            AlertDialog show = new AlertDialog.Builder(getContext())
                .setTitle("Uwaga")
                .setMessage("Czy chcesz zresetowac osiagniecie?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        achievement.setLocked(true);
                        MainActivity.achievementDbHelper.updateAchievement(achievement.getName(),
                                true,
                                achievement.getCorrectAnswersRow(),
                                achievement.getStatus(),
                                achievement.getDifficultLevel());
                        adapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
        return true;
    }

}
