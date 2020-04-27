package com.example.hukin.Logic;

import android.graphics.Canvas;

public class Movement {
    public static void move(final GameObject q, final int x2, final int y2, final double speed,
                            final int left, final int right, final int top, final int bottom) {
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
        }
        //Hi
    }

    private static boolean inRange(final GameObject q, final int x, final int y) {
        final int range = 10;
        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }

    //Checks if sprite doesn't go off edge of arena
    private static boolean checkBoundaries(GameObject q, int x, int y, int left, int right, int top, int bottom) {
        return !(x >= left && x <= right - q.getWidth()/2 && y <= bottom - q.getHeight()/2
                && y >= top);
    }
}
