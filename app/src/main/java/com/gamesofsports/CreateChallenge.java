package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.parseFeatures.ParseFeatures;


public class CreateChallenge extends Activity {
    private NumberPicker    minutes, secondes, difficulty, success;
    private int             minutesChallenge = 0;
    private int             secondesChallenge = 0;
    private int             difficultyChallenge = 1;
    private int             successChallenge = 1;
    private Button          createNewChallenge;
    private TextView        nameSport;
    private EditText        nameChallenge;
    private EditText        descriptionChallenge;
    private String          idSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        createNewChallenge = (Button) findViewById(R.id.createNewChallenge);
        nameSport = (TextView) findViewById(R.id.nameSport);
        minutes = (NumberPicker) findViewById(R.id.minutes);
        secondes = (NumberPicker) findViewById(R.id.secondes);
        difficulty = (NumberPicker) findViewById(R.id.level);
        success = (NumberPicker) findViewById(R.id.success);
        nameChallenge = (EditText) findViewById(R.id.nameNewChallenge);
        descriptionChallenge = (EditText) findViewById(R.id.description);
        minutes.setMinValue(0);
        minutes.setMaxValue(99);
        secondes.setMinValue(0);
        secondes.setMaxValue(59);
        difficulty.setMinValue(1);
        difficulty.setMaxValue(3);
        success.setMinValue(1);
        success.setMaxValue(1000);
        minutes.setWrapSelectorWheel(true);
        secondes.setWrapSelectorWheel(true);
        success.setWrapSelectorWheel(true);


        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nameSport.setText(extras.getStringArray("infosSportChallenge")[0]);
            idSport = extras.getStringArray("infosSportChallenge")[1];
        }
        createNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateChallenge.this, ListChallenges.class);
                startActivity(intent);
                ParseFeatures features = ParseFeatures.getInstance();
                if (features.isInit() == false)
                    features.initializeParseFeatures(getParent());
                if (features.isUserInit() == false)
                    return;
                int time = minutesChallenge * 60 + secondesChallenge;
                features.addChallenge(idSport, nameChallenge.getText().toString(), descriptionChallenge.getText().toString(), time, difficultyChallenge, successChallenge);
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

        difficulty.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                difficultyChallenge = newVal;
            }
        });

        success.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                successChallenge = newVal;
            }
        });
    }

}
