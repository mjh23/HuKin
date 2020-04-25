package com.example.hukin.Logic;

public class SavedData {

    //Stores whether user is continuing/just started a game or not
    //true means there are valid values stored in the class
    //false means all values in class are empty (typically when app restarts)
    public static boolean isOldGame = false;

    //Stores the role
    public static int role = 0;

    //Stores elapsed time
    public static long elapsedTime = 0;

    //Clears the previous saved data
    public static void resetToDefaults() {
        isOldGame = false;
    }
}
