package com.example.hukin.Logic;

import android.graphics.Canvas;

public class levelEnemies {
    public CharacterSprites sprite;
    public int role;
    public int level;
    public Canvas canvas;

    levelEnemies(final int setRole, final int setLevel, final CharacterSprites setSprite, final Canvas setCanvas) {
        role = setRole;
        level = setLevel;
        sprite = setSprite;
        canvas = setCanvas;
    }
}
