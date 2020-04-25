package com.example.hukin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private Button returnbtn;
    private Switch musicToggle;
    private Switch soundeffToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);

        returnbtn = (Button) findViewById(R.id.returnbtn1);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Toggles turning music on or off
        musicToggle = (Switch) findViewById(R.id.toggleMusic);
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SavedData.musicOn = true;
                    Toast.makeText(getApplicationContext(), "Music turned On!", Toast.LENGTH_SHORT).show();
                } else {
                    SavedData.musicOn = false;
                    Toast.makeText(getApplicationContext(), "Music turned Off!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toggles turning sound effects on or off
        soundeffToggle = (Switch) findViewById(R.id.toggleSound);
        soundeffToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SavedData.soundEffOn = true;
                    Toast.makeText(getApplicationContext(), "Sound effects turned On!", Toast.LENGTH_SHORT).show();
                } else {
                    SavedData.soundEffOn = false;
                    Toast.makeText(getApplicationContext(), "Sound effects turned Off!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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