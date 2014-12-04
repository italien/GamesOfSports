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
import android.widget.ListView;

import com.parse.ParseObject;
import com.parseFeatures.ParseFeatures;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ListTeamSports extends Fragment {
    private ParseFeatures parse;
    private ListView listTeamSport;
    private List<ParseObject> listSports;
    private List<String> namesSport = new ArrayList<String>();
    private List<String> idSport = new ArrayList<String>();

    public ListTeamSports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_team_sports, container, false);
        listTeamSport = (ListView) v.findViewById(R.id.listTeamSports);
        parse = ParseFeatures.getInstance();
        if (parse.isInit()==false)
            parse.initializeParseFeatures(getActivity());
        if (parse.isUserInit() == false)
            return null;
        listSports = parse.getObjects("Sports", "idCategory", 1);
        for (int i = 0; i < listSports.size(); i++) {
            namesSport.add(listSports.get(i).getString("name"));
            idSport.add(listSports.get(i).getObjectId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, namesSport);
        listTeamSport.setAdapter(adapter);// Inflate the layout for this fragment
        listTeamSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), ListChallenges.class);
                Bundle infosSport = new Bundle();
                String sportName = listTeamSport.getItemAtPosition(position).toString();
                infosSport.putStringArray("infosSport", new String[]{sportName, listSports.get(position).getObjectId()});
                intent.putExtras(infosSport);
                startActivity(intent);
            }
        });
        return v;
    }
}
