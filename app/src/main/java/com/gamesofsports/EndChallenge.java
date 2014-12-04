package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parseFeatures.ParseFeatures;


public class EndChallenge extends Activity {

    private TextView    results, numberExperience, numberPoints;
    private Button      home, shareFacebook;
    private String      challengeName;
    private String      challengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_challenge);
        home = (Button) findViewById(R.id.Home);
        shareFacebook = (Button) findViewById(R.id.shareFacebook);
        results = (TextView) findViewById(R.id.result);
        numberExperience = (TextView) findViewById(R.id.numberExperience);
        numberPoints = (TextView) findViewById(R.id.numberPoints);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            results.setText(extras.getStringArray("results")[0]);
            numberExperience.setText(extras.getStringArray("results")[1]);
            numberPoints.setText(extras.getStringArray("results")[2]);
            challengeName = extras.getStringArray("results")[3];
            challengeId = extras.getStringArray("results")[4];

            if (extras.getStringArray("results")[0].equals("WIN")) {
                ParseFeatures features = ParseFeatures.getInstance();
                if (features.isInit() == false)
                    features.initializeParseFeatures(this);
                if (features.isUserInit() == true) {
                    features.addUserPoints(Integer.parseInt(extras.getStringArray("results")[1]));
                    features.challengeSucceeded(challengeId);
                    features.checkAchievement();
                }
            }
            Log.d("challengename", challengeName);
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndChallenge.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        shareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndChallenge.this ,ShareActivity.class);
                Bundle points = new Bundle();
                points.putStringArray("points", new String[]{numberPoints.getText().toString(), challengeName});
                intent.putExtras(points);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.end_challenge, menu);
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

    @Override
    public void onBackPressed()
    {
    }
}
