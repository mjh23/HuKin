package com.example.hukin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hukin.Logic.SavedData;

public class MainActivity extends AppCompatActivity {

    private Button playbtn;
    private Button continuebtn;
    private Button settingsbtn;

    //Holds click MediaPlayer object
    MediaPlayer click;
    public static MediaPlayer music;
    public static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If user had backpressed on any activity, exits app
        if(getIntent().getBooleanExtra("EXIT",false)){
            music.stop();
            if(Build.VERSION.SDK_INT >= 16)
                finishAffinity();
            else
                ActivityCompat.finishAffinity(this);
        }

        //Hides android app's home and back buttons
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }

        //Prepares click sound if sound effects are turned on
        click = MediaPlayer.create(MainActivity.this, R.raw.click);
        //Prepares theme music
        if (music != null) {
            music.stop();
            music.release();
            music = null;
        }
        music = MediaPlayer.create(MainActivity.this, R.raw.theme);
        music.setLooping(true);

        if (SavedData.musicOn) {
            try {
                music.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Player clicks on "Start New Game" Button
        playbtn = (Button) findViewById(R.id.playgamebtn);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }
                Intent intent = new Intent(getApplicationContext(), PlayerSettings.class);
                startActivity(intent);
                finish();
            }
        });

        if (SavedData.isOldGame) {
            continuebtn = (Button) findViewById(R.id.continuegamebtn);
            continuebtn.setVisibility(View.VISIBLE);
            continuebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SavedData.soundEffOn) {
                        clickSound();
                    }
                    Intent intent = new Intent(getApplicationContext(), GameArenaHolder.class);
                    intent.putExtra("startNew", false);
                    startActivity(intent);
                    finish();
                }
            });
        }

        settingsbtn = (Button) findViewById(R.id.settingsbtn);
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }
                Intent intent = new Intent(getApplicationContext(), GameSettings.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        click.release();
    }

    //Plays click sound effect
    private void clickSound() {
        try {
            click.stop();
            click.prepare();
            click.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    When user taps on phone's backpress button, prompts user with confirmation to leave app
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null);

        AlertDialog alert = builder.create();
        alert.show();

        TextView messageView = (TextView) alert.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER_HORIZONTAL);
        messageView.setTextSize(20);
    }
}
