package com.example.hukin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.hukin.Logic.Constants;
import com.example.hukin.Logic.SavedData;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlayerSettings extends AppCompatActivity {

    //Store buttons on this activity
    private Button returnbtn;
    private Button playbtn;
    private Button prevBtn;
    private Button nextBtn;

    //Stores the Textview references
    private TextView roleHeader;
    private TextView roleDescrip;
    private TextView health;
    private TextView speed;
    private TextView dex;
    private TextView damage;
    private TextView range;

    //Store which class selected
    private int role = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_settings);

        //Player clicks on "Return" Button
        returnbtn = (Button) findViewById(R.id.returnbtn);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                //Save role selection to savedData
                SavedData.role = role;

                Intent intent = new Intent(getApplicationContext(), GameArenaHolder.class);
                startActivity(intent);
                finish();
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

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = (role + 1) % Constants.getNumRoles();
                updateText(role);
            }
        });

        prevBtn = (Button) findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = (role - 1 + Constants.getNumRoles()) % Constants.getNumRoles();
                updateText(role);
            }
        });
        //End of Selecting different player role
    }

    /**
     * Updates Player Settings Activity role, description, and stats
     * @param role the index of the role
     */
    private void updateText(int role) {
        //Set header
        roleHeader.setText("The " + Constants.getRole(role));
        //Set description
        switch (role) {
            case 0:
                roleDescrip.setText(R.string.warrior_descrip);
                break;
            case 1:
                roleDescrip.setText(R.string.ranger_descrip);
                break;
            case 2:
                roleDescrip.setText(R.string.wizard_descrip);
                break;
            case 3:
                roleDescrip.setText(R.string.bard_descrip);
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
