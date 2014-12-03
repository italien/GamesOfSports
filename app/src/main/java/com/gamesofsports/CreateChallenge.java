package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class CreateChallenge extends Activity {
    private NumberPicker    minutes, secondes;
    private int             minutesChallenge = 0;
    private int             secondesChallenge = 0;
    private Button          createNewChallenge;
    private TextView        nameSport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        createNewChallenge = (Button) findViewById(R.id.createNewChallenge);
        nameSport = (TextView) findViewById(R.id.nameSport);
        minutes = (NumberPicker) findViewById(R.id.minutes);
        secondes = (NumberPicker) findViewById(R.id.secondes);
        minutes.setMinValue(0);
        minutes.setMaxValue(60);
        minutes.setMinValue(0);
        secondes.setMaxValue(60);
        minutes.setWrapSelectorWheel(true);
        secondes.setWrapSelectorWheel(true);


        final Bundle extras = getIntent().getExtras();
        if (extras != null)
            nameSport.setText(extras.getStringArray("infosSportChallenge")[0]);
        createNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateChallenge.this, ListChallenges.class);
                startActivity(intent);
            }
        });
        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutesChallenge = newVal;
            }
        });

        secondes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                secondesChallenge = newVal;
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_challenge, menu);
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
    }*/
}
