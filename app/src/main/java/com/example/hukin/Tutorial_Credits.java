package com.example.hukin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hukin.Logic.SavedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import androidx.appcompat.app.AppCompatActivity;

public class Tutorial_Credits extends AppCompatActivity {

    MediaPlayer click;

    private Button returnbtn;
    private TextView jokeText;

    //For API calls of dad jokes, requestqueue
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_credits);

        //Hides android app's home and back buttons
        immersiveMode();

        //Prepares click sound if sound effects are turned on
        click = MediaPlayer.create(Tutorial_Credits.this, R.raw.click);

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

        jokeText = (TextView) findViewById(R.id.dadjoke);

        //Prepares Dad joke
        mQueue = Volley.newRequestQueue(Tutorial_Credits.this);
        jsonParse();
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

    public void jsonParse() {
        String url = "https://icanhazdadjoke.com/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jokeJson = response;
                            String joke = jokeJson.getString("joke");
                            jokeText.setText(joke);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
