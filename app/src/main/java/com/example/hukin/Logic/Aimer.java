package com.example.hukin.Logic;

import java.util.List;

public class Aimer {
    public static Enemy aim(PlayerStatus p, List<Enemy> l) {
        Enemy min = l.get(0);
        double minDist = getDist(p, min);
        for (Enemy e : l) {
            double dist = getDist(p, e);
            if (minDist > dist) {
                min = e;
                minDist = dist;
            }
        }
        if (minDist <= p.getRange()) {
            return min;
        } else {
            return null;
        }
    }

    public static double getDist(GameObject o1, GameObject o2) {
        return Math.sqrt((o1.getX() - o2.getX())*(o1.getX() - o2.getX()) + (o1.getY() - o2.getY()) * (o1.getY() - o2.getY()));
    }
    public static boolean inRange(GameObject o1, GameObject o2, double range) {
        return getDist(o1, o2) <= range;
    }
}
