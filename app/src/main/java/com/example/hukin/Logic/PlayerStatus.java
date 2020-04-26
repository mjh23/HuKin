package com.example.hukin.Logic;

public class PlayerStatus extends GameObject {
    // Defines the class that the player selected
    private final int playerRole;
    // Defines the player's speed.
    private double speed;
    // Defines teh player's damage.
    private double damage;
    // Defines the player's health.
    private double hitPoints;
    // Defines the player's range of attack.
    private double range;
    // Defines how fast the player shoots.
    private double dex;


    /**
     * Constructs the player avatar.
     * @param setHeight avatar height
     * @param setWidth avatar width
     * @param setRole player's chosen role
     */
    public PlayerStatus(final int setHeight, final int setWidth, final int setRole,
                        final CharacterSprites setSprite) {
        super(setHeight, setWidth, (GameArena.rightBound - GameArena.leftBound)/2,
                (GameArena.bottomBound - GameArena.topBound)/2, setSprite);
        playerRole = setRole;
        speed = Constants.getSpeed(playerRole);
        damage = Constants.getDamage(playerRole);
        hitPoints = Constants.getHitpoints(playerRole);
        range = Constants.getRange(playerRole);
        dex = Constants.getDex(playerRole);
    }

    // A whole bunch of self-explanatory get functions
    public double getPlayerRole() {
        return playerRole;
    }

    public double getSpeed() {
        return speed;
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
