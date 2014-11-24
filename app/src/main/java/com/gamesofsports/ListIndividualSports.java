package com.gamesofsports;



<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> 0cc18f8285ec02ed52e9632f45ea78ed6b1133b1
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.AdapterView;
=======
>>>>>>> 0cc18f8285ec02ed52e9632f45ea78ed6b1133b1
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
<<<<<<< HEAD
    private ListView listIndiviudalSport;
=======
    private ListView listTeamSport;
>>>>>>> 0cc18f8285ec02ed52e9632f45ea78ed6b1133b1
    private List<String> names;

    public ListIndividualSports() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_individual_sports, container, false);
<<<<<<< HEAD
        listIndiviudalSport = (ListView) v.findViewById(R.id.listIndividualSports);
        parse = new ParseFeatures(getActivity());
        names = parse.getObjectName("Sports", "idCategory", 0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        listIndiviudalSport.setAdapter(adapter);         // Inflate the layout for this fragment
        listIndiviudalSport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), ListChallenges.class);
                startActivity(intent);
            }
        });
=======
        listTeamSport = (ListView) v.findViewById(R.id.listIndividualSports);
        parse = new ParseFeatures(getActivity());
        names = parse.getObjectName("Sports", "idCategory", 0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        listTeamSport.setAdapter(adapter);         // Inflate the layout for this fragment
>>>>>>> 0cc18f8285ec02ed52e9632f45ea78ed6b1133b1
        return v;
    }


}
