package com.example.hukin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hukin.Logic.GameArena;
import com.example.hukin.Logic.SavedData;

public class GameArenaHolder extends Activity {

    private GameArena gameView;

    /*
    This class handles intents from Player Settings to play game. Sets up screen for GameArena
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Prepares a full screen setup for GameArena
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        /*View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }*/
        fullScreencall();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Checks whether user is starting a new game or resuming an old one.
        SavedData data = new SavedData();
        Intent received = getIntent();
        Bundle extras = received.getExtras();
        Boolean startNew = true;
        if (extras != null) {
            //startNew only false when player presses "Continue Game" button in Main Activity
            startNew = extras.getBoolean("startNew", true);
        }
        //If there is a saved file and the player doesn't want a new game, we call the load game constructor of GameArena
        //Otherwise, it will always create an entirely new game and overwrite current saved data
        //(typically when the player reaches 'Game over' and has to create a new game even though there is a saved file.
        if (data.isOldGame && startNew) {
            SavedData.resetToDefaults();
        }
        gameView = new GameArena(this);
        setContentView(gameView);
    }

    public void fullScreencall() {
        if(Build.VERSION.SDK_INT > 15 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
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
