package com.gamesofsports;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parseFeatures.ParseFeatures;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ListIndividualSports extends Fragment {

    private ParseFeatures parse;
    private ListView listTeamSport;
    private List<String> names;

    public ListIndividualSports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_individual_sports, container, false);
        listTeamSport = (ListView) v.findViewById(R.id.listIndividualSports);
        parse = new ParseFeatures(getActivity());
        names = parse.getObjectName("Sports", "idCategory", 0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        listTeamSport.setAdapter(adapter);         // Inflate the layout for this fragment
        return v;
    }


}
