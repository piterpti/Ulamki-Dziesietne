package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.*;

import com.example.stacjonarny.graulamki.Classes.AchievementAdapter;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment {


    private ListView achievementList;

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
        Collections.sort(MainActivity.achievementList);
        AchievementAdapter achievementAdapter = new AchievementAdapter(getActivity(), MainActivity.achievementList);
        achievementList.setAdapter(achievementAdapter);
    }

}
