package com.example.hukin.Logic;

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
    Enemy (final int x, final int y, final CharacterSprites setSprite, final double setSpeed, final int setRole) {
        super(x, y, setSprite, Constants.getSpeed(setRole));
        enemyRole = setRole;
        speed = Constants.getSpeed(enemyRole);
        damage = Constants.getDamage(enemyRole);
        hitPoints = Constants.getHitpoints(enemyRole);
        range = Constants.getRange(enemyRole);
        dex = Constants.getDex(enemyRole);

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
}
