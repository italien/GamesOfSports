package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parseFeatures.ParseFeatures;

import java.util.ArrayList;
import java.util.List;


public class ListChallenges extends Activity {
    private ParseFeatures parse;
    private ListView    listChallenges;
    private List<ParseObject> listChallengesObjects;
    private List<String>    nameChallenges = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Button          create;
    private String          sportName;
    private String          idSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_challenges);
        final Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            sportName = extras.getStringArray("infosSport")[0];
            idSport = extras.getStringArray("infosSport")[1];
        }
        parse = ParseFeatures.getInstance();
        if (parse.isInit()==false)
            parse.initializeParseFeatures(this);
        if (parse.isUserInit() == false)
            return;
        listChallengesObjects = parse.getObjects("Challenges", "idSport", idSport);
        for (int i = 0; i < listChallengesObjects.size(); i++)
        {
            Log.d("name", listChallengesObjects.get(i).getString("challengeName"));
            String challengeName = listChallengesObjects.get(i).getString("challengeName") + " - Difficulty " + Integer.toString(listChallengesObjects.get(i).getInt("difficulty"));
            nameChallenges.add(challengeName);
        }

        listChallenges= (ListView) findViewById(R.id.listChallenges);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameChallenges);
        listChallenges.setAdapter(adapter); // Inflate the layout for this fragment
        listChallenges.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(ListChallenges.this, DescriptionChallenge.class);
                Bundle infosSportChallenge = new Bundle();
                infosSportChallenge.putStringArray("infosChallenge", new String[]{listChallengesObjects.get(position).getString("challengeContent"), listChallengesObjects.get(position).getObjectId()});
                intent.putExtras(infosSportChallenge);
                startActivity(intent);
            }
        });
        create = (Button) findViewById(R.id.createChallenge);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListChallenges.this, CreateChallenge.class);
                Bundle infosSportChallenge = new Bundle();
                infosSportChallenge.putStringArray("infosSportChallenge", new String[]{sportName, idSport});
                intent.putExtras(infosSportChallenge);
                startActivity(intent);
            }
        });
    }

}
