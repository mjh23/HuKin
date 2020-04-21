package com.example.hukin.Logic;

public class Constants {

    //Array to organize and store role data
    //Index 0 - Warrior
    //Index 1 - Ranger
    //Index 2 - Wizard
    //Index 3 - Bard

    private static double[] speed = {5, 10, 7, 9};
    private static double[] damage = {10, 5, 15, 30};
    private static double[] hitpoints = {250, 150, 125, 150};
    private static double[] range = {3, 10, 6, 7};
    private static double[] dex = {5, 5, 5, 0};

    public static double getSpeed(int index) {
        return speed[index];
    }

    public static double getDamage(int index) {
        return damage[index];
    }

    public static double getHitpoints(int index) {
        return hitpoints[index];
    }

    public static double getRange(int index) {
        return range[index];
    }

    public static double getDex(int index) {
        return dex[index];
    }
}
