package com.gamesofsports;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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
public class ListIndividualSports extends Fragment {

    private ParseFeatures parse;
    private ListView listIndiviudalSport;
    private ListView listTeamSport;
    private List<ParseObject> listSports;
    private List<String> namesSport = new ArrayList<String>();

    public ListIndividualSports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_individual_sports, container, false);
        listIndiviudalSport = (ListView) v.findViewById(R.id.listIndividualSports);
        parse = ParseFeatures.getInstance();
        listSports = parse.getObjects("Sports", "idCategory", 0);
        for (int i = 0; i < listSports.size(); i++)
            namesSport.add(listSports.get(i).getString("name"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, namesSport);
        listIndiviudalSport.setAdapter(adapter);         // Inflate the layout for this fragment
        listIndiviudalSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), ListChallenges.class);
                Bundle infosSport = new Bundle();
                String sportName = listIndiviudalSport.getItemAtPosition(position).toString();
                infosSport.putStringArray("infosSport", new String[]{sportName, listSports.get(position).getObjectId()});
                intent.putExtras(infosSport);
                startActivity(intent);
            }
        });
       // Inflate the layout for this fragment
        return v;
    }


}
