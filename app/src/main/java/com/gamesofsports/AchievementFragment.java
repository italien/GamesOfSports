package com.gamesofsports;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.ParseObject;
import com.parseFeatures.ParseFeatures;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AchievementFragment extends Fragment {
    private ParseFeatures parse;
    private ListView listAchievements;
    private List<ParseObject> achievements;
    private List<String> namesAchievements = new ArrayList<String>();;


    public AchievementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_achievement, container, false);
        listAchievements= (ListView) v.findViewById(R.id.listAchievements);
        parse = ParseFeatures.getInstance();
        if (parse.isInit()==false)
            parse.initializeParseFeatures(getActivity());
        if (parse.isUserInit() == false)
            return null;
        achievements = parse.getAllObjects("Achievements");

        for (int i = 0; i < achievements.size(); i++) {
            namesAchievements.add(achievements.get(i).getString("name"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, namesAchievements);
        listAchievements.setAdapter(adapter);// Inflate the layout for this fragment
        listAchievements.setEnabled(false);
        listAchievements.setChoiceMode(2);

        for (int i = 0; i < achievements.size(); i++) {
            if (parse.checkAchievement(achievements.get(i).getObjectId()))
                listAchievements.setItemChecked(i, true);
            else
                listAchievements.setItemChecked(i, false);
        }
        return v;
    }


}
