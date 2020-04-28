package com.example.hukin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hukin.Logic.Constants;
import com.example.hukin.Logic.SavedData;

import org.json.JSONArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PlayerSettings extends AppCompatActivity {

    MediaPlayer click;

    //Store buttons on this activity
    private Button returnbtn;
    private Button playbtn;
    private Button prevBtn;
    private Button nextBtn;
    private Button genderBtn;

    //Stores the Textview references
    private TextView roleHeader;
    private TextView roleDescrip;
    private TextView health;
    private TextView speed;
    private TextView dex;
    private TextView damage;
    private TextView range;

    //Stores Imageview for changing the sprite displayed
    private ImageView displaySprite;

    //Stores background image
    private ImageView background;

    //Stores player's typed character name
    private EditText charName;

    //Store which class selected
    private int role = 0;

    //For API calls of random names, requestqueue
    private RequestQueue mQueue;

    //Stores the gender of the char name
    //True = Male
    //False = Female
    private boolean charGender;

    //Desired character name. Default: Jack Ashwald
    private String name = "Jack Ashwald";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_settings);

        //Prepares click sound if sound effects are turned on
        click = MediaPlayer.create(PlayerSettings.this, R.raw.click);

        //Player clicks on "Return" Button
        returnbtn = (Button) findViewById(R.id.returnbtn1);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Player clicks on "Play" Button
        playbtn = (Button) findViewById(R.id.playgamebtn);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }
                //Save role selection to savedData
                SavedData.role = role;
                //Sets text entered in charName to character name
                if (!TextUtils.isEmpty(charName.getText())) {
                    if (charName.getText().toString().length() >= 20) {
                        Toast.makeText(getApplicationContext(), "Name not saved. Max word length is 20 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        SavedData.characterName = charName.getText().toString();
                    }
                } else {
                    if (name.length() >= 20) {
                        String[] cut = name.split(" ");
                        SavedData.characterName = cut[0];
                    } else {
                        SavedData.characterName = name;
                    }
                }
                Intent intent = new Intent(getApplicationContext(), GameArenaHolder.class);
                startActivity(intent);
                finish();
            }
        });

        //EditText view of the player settings
        charName = (EditText) findViewById(R.id.charName);
        charName.setHint(name);

        //Generates a single random name from API call that fits the displayed gender by setting
        genderBtn = (Button) findViewById(R.id.genderbtn);
        genderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }

                System.out.println(name);
                //Makes API call
                if (ContextCompat.checkSelfPermission(PlayerSettings.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                    mQueue = Volley.newRequestQueue(PlayerSettings.this);
                    jsonParse();
                } else {
                    Toast.makeText(getApplicationContext(), "For custom names, please allow permissions: Internet", Toast.LENGTH_LONG).show();
                    return;
                }

                //If displayed is M, then clicking turns to to F and vise versa
                if (genderBtn.getText().equals("M")) {
                    charGender = true;
                    genderBtn.setText("F");
                } else {
                    charGender = false;
                    genderBtn.setText("M");
                }
            }
        });

        //Player Selects Different Player Role
        //Find All TextViews, then when next or prev button pressed
        //Go to relevant index number
        roleHeader = (TextView) findViewById(R.id.roleSelect);
        roleDescrip = (TextView) findViewById(R.id.classDescrip);
        health = (TextView) findViewById(R.id.healthTextView);
        speed = (TextView) findViewById(R.id.speedTextView);
        dex = (TextView) findViewById(R.id.dexterityTextView);
        damage = (TextView) findViewById(R.id.damageTextView);
        range = (TextView) findViewById(R.id.rangeTextView);

        updateText(role);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }

                role = (role + 1) % Constants.getNumRoles();
                updateText(role);
            }
        });

        prevBtn = (Button) findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.soundEffOn) {
                    clickSound();
                }

                role = (role - 1 + Constants.getNumRoles()) % Constants.getNumRoles();
                updateText(role);
            }
        });
        //End of Selecting different player role
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

    //Makes an API call for random names
    public void jsonParse() {
        String url = "";
        if (charGender) {
            if (Math.random() >= 0.3) {
                url = "http://names.drycodes.com/1?nameOptions=boy_names&separator=space";
            } else {
                url = "http://names.drycodes.com/1?nameOptions=funnyWords&separator=space";
            }
        } else {
            url = "http://names.drycodes.com/1?separator=space&nameOptions=girl_names";
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        name = response.toString()
                                .replaceAll("[\\[\\](){}]","")
                                .replaceAll("\"","");
                        if (name.length() >= 20) {
                            String[] cut = name.split(" ");
                            charName.setHint(cut[0]);
                        } else {
                            charName.setHint(name);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        System.out.println(name);
        mQueue.add(request);
    }

    /**
     * Updates Player Settings Activity role, description, and stats
     * @param role the index of the role
     */
    private void updateText(int role) {
        //Set header
        roleHeader.setText("The " + Constants.getRole(role));

        //Set description & (combined with)
        //Change displayed sprite to match role
        displaySprite = (ImageView) findViewById(R.id.imageView);
        switch (role) {
            case 0:
                roleDescrip.setText(R.string.warrior_descrip);
                displaySprite.setImageResource(R.drawable.char_armor);
                break;
            case 1:
                roleDescrip.setText(R.string.ranger_descrip);
                displaySprite.setImageResource(R.drawable.ranger);
                break;
            case 2:
                roleDescrip.setText(R.string.wizard_descrip);
                displaySprite.setImageResource(R.drawable.wizard);
                break;
            case 3:
                roleDescrip.setText(R.string.bard_descrip);
                displaySprite.setImageResource(R.drawable.bard);
                break;
        }

        //Set stats, SpannableString used to highlight numbers
        String h = "Hitpoints: " + Constants.getHitpoints(role);
        SpannableString spannableString = new SpannableString(h);
        ForegroundColorSpan fcsLime = new ForegroundColorSpan(getResources().getColor(R.color.lime));
        spannableString.setSpan(fcsLime,11,h.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        health.setText(spannableString);

        h = "Speed: " + Constants.getSpeed(role);
        spannableString = new SpannableString(h);
        spannableString.setSpan(fcsLime,7,h.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        speed.setText(spannableString);

        h = "Dexterity: " + Constants.getDex(role);
        spannableString = new SpannableString(h);
        spannableString.setSpan(fcsLime,11,h.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        dex.setText(spannableString);

        h = "Damage: " + Constants.getDamage(role);
        spannableString = new SpannableString(h);
        spannableString.setSpan(fcsLime,8,h.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        damage.setText(spannableString);

        h = "Range: " + Constants.getRange(role);
        spannableString = new SpannableString(h);
        spannableString.setSpan(fcsLime,6,h.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        range.setText(spannableString);
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
