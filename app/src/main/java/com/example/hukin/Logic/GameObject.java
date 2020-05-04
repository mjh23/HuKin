package com.example.hukin.Logic;

import android.graphics.Canvas;

/**
 * Defines the basic qualities of any object we create in the game.
 */
public abstract class GameObject {
    /** width of image. */

    /** x-coordinate of image location. */
    protected int x;
    /** y-coordinate of image location. */
    protected int y;
    private CharacterSprites sprite;
    protected Canvas canvas;

    /**
     * Constructs Game Object.
     * @param setX sets the x-coordinate of the objects location
     * @param setY sets the y-coordinate of the objects location
     */
    public GameObject( final int setX, final int setY,
                      final CharacterSprites setSprite, final Canvas setCanvas) {
        canvas = setCanvas;
        x = setX;
        y = setY;
        sprite = setSprite;
    }
    public GameObject (final int setX, final int setY, final Canvas setCanvas) {
        canvas = setCanvas;
        x = setX;
        y = setY;
        sprite = null;
    }

    /**
     * Getter for avatar width.
     * @return width of the object avatar
     */


    /**
     * Getter for avatar height.
     * @return height of the object's avatar
     */


    /**
     * Getter for the x-ccordinate of the object's location.
     * @return x-coordinate of the location
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate of the object's location.
     * @return y-coordinate of the location
     */
    public int getY() {
        return y;
    }
    public void setX(int newX) { x = newX; }
    public void setY(int newY) { y = newY; }

    //Get sprite
    public CharacterSprites getSprite() {
        return sprite;
    }
}
