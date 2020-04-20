package com.example.hukin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button playbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If user had backpressed on any activity, exits app
        if(getIntent().getBooleanExtra("EXIT",false)){
            if(Build.VERSION.SDK_INT >= 16)
                finishAffinity();
            else
                ActivityCompat.finishAffinity(this);
        }

        //Player clicks on "Start New Game" Button
        playbtn = (Button) findViewById(R.id.playgamebtn);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerSettings.class);
                startActivity(intent);
                finish();
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
