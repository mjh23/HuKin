package com.example.hukin.Logic;

import android.graphics.Canvas;

public class Moveable extends GameObject {
    protected double speed;
    protected Movement move;
    Moveable(final int x, final int y, final CharacterSprites setSprite, final double setSpeed, final Canvas setCanvas) {
        super(x, y, setSprite, setCanvas);
        speed = setSpeed;
        move = new Movement(this, this.getSprite());
    }
    Moveable(final int x, final int y, final CharacterSprites setSprite, final double setSpeed, final Canvas setCanvas, PlayerStatus player) {
        super(x, y, setSprite, setCanvas);
        speed = setSpeed;
        move = new Movement(this, this.getSprite(), player);
    }
    Moveable(final int x, final int y, final double setSpeed, final Canvas setCanvas, GameObject p) {
        super(x, y, setCanvas);
        speed = setSpeed;
        move = new Movement(this, p);
    }



    public double getSpeed() {
        return speed;
    }
}
