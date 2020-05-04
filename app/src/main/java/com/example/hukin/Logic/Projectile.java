package com.example.hukin.Logic;

import java.util.List;

public class Projectile extends Moveable {
    private double damage;
    Projectile (Enemy q, PlayerStatus p) {
        super(q.getX(), q.getY(), 10, q.canvas, p);
        damage = q.getDamage();
    }
    Projectile (PlayerStatus q, List<Enemy> enemies) {
        super(q.getX(), q.getY(), 10, q.canvas, aim(q, enemies));
        damage = q.getDamage();
    }
    public double getDamage() {
        return damage;
    }

    private static Enemy aim(PlayerStatus p, List<Enemy> l) {
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
    public static void damage(Moveable q1, Projectile q2) {
        if (q1 instanceof Enemy) {
            Enemy q = (Enemy) q1;
            q.changeHealth(q2.getDamage());
        } else {
            PlayerStatus q = (PlayerStatus) q1;
            q.changeHealth(q2.getDamage());
        }
    }
    public static void damage(Enemy e, PlayerStatus p) {
        if (inRange(e, p, p.getRange())) {
            e.changeHealth(p.getDamage());
        } else {
            return;
        }
    }

}
