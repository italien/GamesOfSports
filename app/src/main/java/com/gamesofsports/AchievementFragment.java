package com.gamesofsports;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parseFeatures.ParseFeatures;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AchievementFragment extends Fragment {
    private ParseFeatures parse;
    private ListView listAchievements;
    //private List<String> namesAchievements;


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
        // namesAchievements = parse.getObjectName("Sports", "idCategory", 1);
        String[]namesAchievements =  {"Achievement1", "Achievement2", "Achievement3", "Achievement4", "Achievement5","Achievement6", "Achievement7", "Achievement8", "Achievement9", "Achievement10","Achievement11", "Achievement12", "Achievement13", "Achievement14", "Achievement15"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, namesAchievements);
        listAchievements.setAdapter(adapter);// Inflate the layout for this fragment
        listAchievements.setEnabled(false);
        listAchievements.setChoiceMode(2);
        listAchievements.setItemChecked(0, true);
        listAchievements.setItemChecked(2, true);
        listAchievements.setItemChecked(5, true);
        listAchievements.setItemChecked(8, true);
        return v;
    }


}
