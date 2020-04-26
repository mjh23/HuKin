package com.example.hukin.Logic;

/**
 * Defines the basic qualities of any object we create in the game.
 */
public abstract class GameObject {
    /** width of image. */
    protected final int width;
    /** height of image. */
    protected final int height;
    /** x-coordinate of image location. */
    protected int x;
    /** y-coordinate of image location. */
    protected int y;
    private CharacterSprites sprite;

    /**
     * Constructs Game Object.
     * @param setWidth sets width of the object's avatar
     * @param setHeight sets height of the object's avatar
     * @param setX sets the x-coordinate of the objects location
     * @param setY sets the y-coordinate of the objects location
     */
    public GameObject(final int setWidth, final int setHeight, final int setX, final int setY,
                      final CharacterSprites setSprite) {
        width = setWidth;
        height = setHeight;
        x = setX;
        y = setY;
        sprite = setSprite;
    }

    /**
     * Getter for avatar width.
     * @return width of the object avatar
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for avatar height.
     * @return height of the object's avatar
     */
    public int getHeight() {
        return height;
    }

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
}
