package com.example.hukin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hukin.Logic.SavedData;

import androidx.appcompat.app.AppCompatActivity;

public class GameSettings extends AppCompatActivity {

    MediaPlayer click;

    private Button returnbtn;
    private Switch musicToggle;
    private Switch soundeffToggle;
    private Button clearbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);

        //Hides android app's home and back buttons
        immersiveMode();

        //Prepares click sound if sound effects are turned on
        click = MediaPlayer.create(GameSettings.this, R.raw.click);

        returnbtn = (Button) findViewById(R.id.returnbtn1);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Toggles turning music on or off
        musicToggle = (Switch) findViewById(R.id.toggleMusic);
        musicToggle.setChecked(SavedData.musicOn);
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }

                if (b) {
                    SavedData.musicOn = true;
                    Toast.makeText(getApplicationContext(), "Music turned On!", Toast.LENGTH_SHORT).show();
                } else {
                    SavedData.musicOn = false;
                    Toast.makeText(getApplicationContext(), "Music turned Off!", Toast.LENGTH_SHORT).show();
                    MainActivity.music.stop();
                    MainActivity.isPlaying = false;
                }
            }
        });

        //Toggles turning sound effects on or off
        soundeffToggle = (Switch) findViewById(R.id.toggleSound);
        soundeffToggle.setChecked(SavedData.soundEffOn);
        soundeffToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }

                if (b) {
                    SavedData.soundEffOn = true;
                    Toast.makeText(getApplicationContext(), "Sound effects turned On!", Toast.LENGTH_SHORT).show();
                } else {
                    SavedData.soundEffOn = false;
                    Toast.makeText(getApplicationContext(), "Sound effects turned Off!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //When player pushes clear saved game
        clearbtn = (Button) findViewById(R.id.cleargamebtn);
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Confirms with user of this action
                confirmationAlert();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        immersiveMode();
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

    //Confirms with user about clearing saved game
    private void confirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to clear saved game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavedData.isOldGame = false;
                        Toast.makeText(getApplicationContext(), "Cleared Saved Game!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null);

        AlertDialog alert = builder.create();
        alert.show();
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

    //Hides android app's home and back buttons
    public void immersiveMode() {
        final View decorView = getWindow().getDecorView();
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
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        immersiveMode();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        immersiveMode();
                    }
                });
    }
}