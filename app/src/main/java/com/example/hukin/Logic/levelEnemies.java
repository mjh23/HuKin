package com.example.hukin.Logic;

import android.graphics.Canvas;

public class levelEnemies {
    public CharacterSprites sprite;
    public int role;
    public int level;
    public Canvas canvas;
    public PlayerStatus playTarg;

    levelEnemies(final int setRole, final int setLevel, final CharacterSprites setSprite, final Canvas setCanvas, final PlayerStatus player) {
        role = setRole;
        level = setLevel;
        sprite = setSprite;
        canvas = setCanvas;
        playTarg = player;
    }
}
