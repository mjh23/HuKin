package com.example.hukin.Logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprites {

    //Stores all the bitmaps used in game
    private Bitmap image;

    public CharacterSprites(Bitmap bmp) {
        image = Bitmap.createScaledBitmap(bmp, 128,128, false);
    }

    /**
     * Draws to canvas the stored bitmap, at the x and y locations
     * @param canvas the canvas we are drawing to, usually just pass "canvas" from GameArena
     * @param x x coordinate of the object
     * @param y y coordinate of the object
     */
    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(image, x, y, null);
    }


}
