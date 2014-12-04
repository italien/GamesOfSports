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


public class StartChallenge extends Activity {

    private     Button          start, addPoints, removePoints;
    private     TextView        countDown, textPoints;
    private     Boolean         started = false;
    private     CountDownTimer  cdTimer;
    private     long            total;
    private     int             points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_challenge);

        start = (Button) findViewById(R.id.startStop);
        addPoints = (Button) findViewById(R.id.addPoints);
        removePoints = (Button) findViewById(R.id.removePoints);
        textPoints = (TextView) findViewById(R.id.textCounter);
        countDown = (TextView) findViewById(R.id.chrono);

        countDown.setText("0:30");
        total = 3 * 1000;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started == false)
                {
                    start.setText("Stop");
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
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                countDown.setText("" + minutes + ":"
                        + String.format("%02d", seconds));
            }
            public void onFinish() {
                //add Intent
                Intent intent = new Intent(StartChallenge.this, EndChallenge.class);
                Bundle results = new Bundle();
                results.putStringArray("results", new String[]{"WIN", "300"});
                intent.putExtras(results);
                startActivity(intent);
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_challenge, menu);
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
