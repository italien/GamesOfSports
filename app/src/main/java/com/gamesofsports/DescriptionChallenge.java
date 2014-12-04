package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.widget.FacebookDialog;


public class DescriptionChallenge extends Activity {

    private TextView    descriptionChallenge;
    private String      idChallenge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_challenge);
        final Button shareButton = (Button) findViewById(R.id.share);
        final Button acceptButton = (Button) findViewById(R.id.accept);
        descriptionChallenge = (TextView) findViewById(R.id.descriptionChallenge);
        final Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            descriptionChallenge.setText(extras.getStringArray("infosChallenge")[0]);
            idChallenge = extras.getStringArray("infosChallenge")[1];
        }
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FacebookDialog.MessageDialogBuilder builder = new FacebookDialog.MessageDialogBuilder(DescriptionChallenge.this)
                        .setLink("https://developers.facebook.com/docs/android/share/")
                        .setName("Try to beat me on this challenge")
                        .setCaption("Try to beat your friends.")
                        .setPicture("http://i.imgur.com/L49TtVW.png?1")
                        .setDescription(descriptionChallenge.getText().toString());
                // If the Facebook app is installed and we can present the share dialog
                if (builder.canPresent()) {
                    FacebookDialog dialog = builder.build();
                    dialog.present();
                }  else {
                    // Disable button or other UI for Message Dialog
                }
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(DescriptionChallenge.this, StartChallenge.class);
                Bundle descriptionChallenges = new Bundle();
                descriptionChallenges.putStringArray("descriptionChallenges", new String[]{idChallenge});
                intent.putExtras(descriptionChallenges);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.description_challenge, menu);
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
