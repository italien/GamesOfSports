package com.gamesofsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parseFeatures.ParseFeatures;


public class StartChallenge extends Activity {

    private     Button          start, addPoints, removePoints, stop;
    private     TextView        countDown, textPoints;
    private     Boolean         started = false;
    private     CountDownTimer  cdTimer;
    private     long            total = 0;
    private     int             points = 0;
    private     String          idChallenge;
    private     ParseFeatures   parse;
    private     ParseObject     challengeObject;
    private     int             timeChallenge, seconds, minutes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_challenge);
        final Bundle extras = getIntent().getExtras();
        if (extras != null)
            idChallenge = extras.getStringArray("descriptionChallenges")[0];
        parse = ParseFeatures.getInstance();
        if (parse.isInit()==false)
            parse.initializeParseFeatures(this);
        if (parse.isUserInit() == false)
            return;
        challengeObject = parse.getObjectWithId("Challenges", idChallenge);
        start = (Button) findViewById(R.id.startPause);
        stop = (Button) findViewById(R.id.stop);
        addPoints = (Button) findViewById(R.id.addPoints);
        removePoints = (Button) findViewById(R.id.removePoints);
        textPoints = (TextView) findViewById(R.id.textCounter);
        countDown = (TextView) findViewById(R.id.chrono);
        timeChallenge = Integer.parseInt(challengeObject.getString("time"));
        minutes = timeChallenge / 60;
        seconds = seconds % 60;
        countDown.setText("" + minutes + ":"
                + String.format("%02d", seconds));
        total = timeChallenge * 1000;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started == false)
                {
                    start.setText("Pause");
                    started = true;
                    startCountDownTimer();
                }
                else
                {
                    start.setText("Start");
                    started = false;
                    cdTimer.cancel();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdTimer.cancel();
                Intent intent = new Intent(StartChallenge.this, EndChallenge.class);
                Bundle results = new Bundle();
                if (points >= challengeObject.getInt("successCondition"))
                    results.putStringArray("results", new String[]{"WIN", Integer.toString(challengeObject.getInt("successPoints")), Integer.toString(points), challengeObject.getString("challengeName"), challengeObject.getObjectId()});
                else
                    results.putStringArray("results", new String[]{"LOSE", "0", Integer.toString(points), challengeObject.getString("challengeName"), challengeObject.getObjectId()});
                intent.putExtras(results);
                startActivity(intent);
            }
        });
        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points += 1;
                textPoints.setText(Integer.toString(points));
            }
        });
        removePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (points >= 1)
                {
                    points -= 1;
                    textPoints.setText(Integer.toString(points));
                }
            }
        });
    }

    private void startCountDownTimer()
    {
            cdTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                //update total with the remaining time left
                total = millisUntilFinished;
                seconds = (int) (millisUntilFinished / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                countDown.setText("" + minutes + ":"
                        + String.format("%02d", seconds));
            }
            public void onFinish() {
                //add Intent
                Intent intent = new Intent(StartChallenge.this, EndChallenge.class);
                Bundle results = new Bundle();
                if (points >= challengeObject.getInt("successCondition"))
                    results.putStringArray("results", new String[]{"WIN", Integer.toString(challengeObject.getInt("successPoints")), Integer.toString(points), challengeObject.getString("challengeName"), challengeObject.getObjectId()});
                else
                    results.putStringArray("results", new String[]{"LOSE", "0", Integer.toString(points), challengeObject.getString("challengeName"), challengeObject.getObjectId()});
                intent.putExtras(results);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed()
    {
    }
}
