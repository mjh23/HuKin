package com.example.hukin.Logic;

import android.graphics.Canvas;

public class Enemy extends Moveable {
    private final int enemyRole;
    // Defines the player's speed.
    // Defines teh player's damage.
    private double damage;
    // Defines the player's health.
    private double hitPoints;
    // Defines the player's range of attack.
    private double range;
    // Defines how fast the player shoots.
    private double dex;
    Enemy (final int x, final int y, final CharacterSprites setSprite, final int setRole, final int level, final Canvas setCanvas, final PlayerStatus player) {
        super(x, y, setSprite, Constants.getSpeed(setRole), setCanvas, player);
        enemyRole = setRole;
        speed = Constants.getSpeed(enemyRole)/3;
        damage = Constants.getDamage(enemyRole) * level / 10;
        hitPoints = Constants.getHitpoints(enemyRole) * level / 10;
        range = Constants.getRange(enemyRole) * 150;
        dex = Constants.getDex(enemyRole) / 2;

    }

    public double getPlayerRole() {
        return enemyRole;
    }

    public double getDamage() {
        return damage;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public double getDex() {
        return dex;
    }

    public double getRange() {
        return range;
    }
    public void changeHealth(double dmg) { hitPoints -= dmg; }


}
