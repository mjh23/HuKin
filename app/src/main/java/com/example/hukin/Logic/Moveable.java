package com.example.hukin.Logic;

public class Moveable extends GameObject {
    protected double speed;
    Moveable(final int x, final int y, final CharacterSprites setSprite, final double setSpeed) {
        super(x, y, setSprite);
        speed = setSpeed;
    }

    public double getSpeed() {
        return speed;
    }
}
