package com.example.hukin.Logic;

import java.util.List;

public class Projectile extends Moveable {
    private double damage;
    Projectile (Enemy q, PlayerStatus p) {
        super(q.getX() - 64, q.getY() - 64, 10, q.canvas, p);
        damage = q.getDamage();
    }
    Projectile (PlayerStatus q, List<Enemy> enemies) {
        super(q.getX() -64, q.getY() -64, 10, q.canvas, Aimer.aim(q, enemies));
        damage = q.getDamage();
    }
    public double getDamage() {
        return damage;
    }

}
