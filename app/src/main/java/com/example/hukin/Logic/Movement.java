package com.example.hukin.Logic;

import android.graphics.Canvas;

public class Movement {
    public static void move(final GameObject q, final int x2, final int y2, final double speed,
                            final Canvas canvas, final CharacterSprites sprite) {
        long start = System.nanoTime();
        int x1 = q.getX();
        int y1 = q.getY();
        double distance = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        double Velocity = speed * 0.1;
        double time = distance/Velocity;
        double xSpeed = (x1-x2)/time;
        double ySpeed = (y1-y2)/time;

        for (;;) {
            if (inRange(q, x2, y2)) {
                break;
            }
            long end = System.nanoTime();
            long deltaTime = (end - start) / 1000000;
            q.setX((int) (x1 - xSpeed * deltaTime));
            q.setY((int) (y1 - ySpeed * deltaTime));
            sprite.draw(canvas, q.getX() - 64, q.getY() + 64);
        }
        //Hi
    }

    private static boolean inRange(final GameObject q, final int x, final int y) {
        final int range = 10;
        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }
}
