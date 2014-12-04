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
        listChallengesObjects = parse.getObjects("Challenges", "idSport", idSport);
        for (int i = 0; i < listChallengesObjects.size(); i++)
            nameChallenges.add(listChallengesObjects.get(i).getString("challengeName"));
        listChallenges= (ListView) findViewById(R.id.listChallenges);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameChallenges);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_challenges, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
