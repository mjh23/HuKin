package com.example.hukin.Logic;

public class Damager {
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
        if (Aimer.inRange(e, p, p.getRange())) {
            e.changeHealth(p.getDamage());
        } else {
            return;
        }
    }
}
