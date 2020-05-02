package com.example.hukin.Logic;

import android.graphics.Canvas;

public class Movement {
    final Moveable v;
    final Canvas canvi;
    final CharacterSprites spriter;
    private int targX;
    private int targY;
    Movement(final Moveable setV, final CharacterSprites setSprite) {
        v = setV;
        targX = v.getX();
        targY = v.getY();
        canvi = setV.canvas;
        spriter = setSprite;
    }
    Movement(final Moveable setV, final CharacterSprites setSprite, final PlayerStatus player) {
        v = setV;
        targX = player.getX();
        targY = player.getY();
        canvi = setV.canvas;
        spriter = setSprite;
    }

    public void setTarg(final int newX, final int newY) {
        targX = newX;
        targY = newY;
    }
    public void move2() {
        int x1 = v.getX();
        int y1 = v.getY();
        double distance = Math.sqrt((x1-targX)*(x1-targX)+(y1-targY)*(y1-targY));
        double Velocity = v.getSpeed() * 3;
        double time = distance/Velocity;
        double xSpeed = (x1-targX)/time;
        double ySpeed = (y1-targY)/time;
        if (v instanceof PlayerStatus) {
            PlayerStatus z = (PlayerStatus) v;
            if (inRange(z, targX, targY)) {
                return;
            }
        } else {
            Enemy z = (Enemy) v;
            if (inRange(z, targX, targY)) {
                return;
            }
        }
        v.setX((int) (x1 - xSpeed));
        v.setY((int) (y1 - ySpeed));
        spriter.draw(canvi, v.getX() - 64, v.getY() + 64);
    }
    public static void massMover (final Moveable[] arr) {
        Movement[] arr2 = new Movement[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i].move;
        }
        for (Movement q : arr2) {
            q.move2();
        }
    }
    public static void move(final Moveable q, final int x2, final int y2,
                            final Canvas canvas, final CharacterSprites sprite) {
        long start = System.nanoTime();
        int x1 = q.getX();
        int y1 = q.getY();
        double distance = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        double Velocity = q.getSpeed() * 0.1;
        double time = distance/Velocity;
        double xSpeed = (x1-x2)/time;
        double ySpeed = (y1-y2)/time;

        for (;;) {
            if (q instanceof PlayerStatus) {
                PlayerStatus z = (PlayerStatus) q;
                if (inRange(z, x2, y2)) {
                    break;
                }
            } else {
                Enemy z = (Enemy) q;
                if (inRange(z, x2, y2)) {
                    break;
                }
            }
            long end = System.nanoTime();
            long deltaTime = (end - start) / 1000000;
            q.setX((int) (x1 - xSpeed * deltaTime));
            q.setY((int) (y1 - ySpeed * deltaTime));
            sprite.draw(canvas, q.getX() - 64, q.getY() + 64);
        }
        //Hi
    }

    private static boolean inRange(final PlayerStatus q, final int x, final int y) {
        final int range = 10;
        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }

    private static boolean inRange(final Enemy q, final int x, final int y) {
        final double range = q.getRange()/2;
        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }


}
