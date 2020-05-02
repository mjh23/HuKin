package com.example.hukin.Logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

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
    Movement(final Moveable setV, GameObject p) {
        v = setV;
        if (p == null) {
            targX = v.getX();
            targY = v.getY();
        } else {
            targX = p.getX() - 64;
            targY = p.getY() -32;
        }
        canvi = setV.canvas;
        spriter = null;
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
        } else if (v instanceof Enemy) {
            Enemy z = (Enemy) v;
            if (inRange(z, targX, targY)) {
                return;
            }
        }
        v.setX((int) (x1 - xSpeed));
        v.setY((int) (y1 - ySpeed));
    }
    public void fire() {
        int x1 = v.getX();
        int y1 = v.getY();
        double distance = Math.sqrt((x1-targX)*(x1-targX)+(y1-targY)*(y1-targY));
        double Velocity = v.getSpeed() * 3;
        double time = distance/Velocity;
        double xSpeed = (x1-targX)/time;
        double ySpeed = (y1-targY)/time;
        Projectile p = (Projectile) v;
        if (inRange(p, targX, targY)) {
            return;
        }
        v.setX((int) (x1 - xSpeed));
        v.setY((int) (y1 - ySpeed));
    }
    public static void massMover (final List<Enemy> arr) {
        Movement[] arr2 = new Movement[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            arr2[i] = arr.get(i).move;
        }
        for (Movement q : arr2) {
            q.move2();
        }
    }
    public static void massFire (final List<Projectile> arr) {
        Movement[] arr2 = new Movement[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            arr2[i] = arr.get(i).move;
        }
        for (Movement q : arr2) {
            q.fire();
        }
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
    private static boolean inRange(final Projectile q, final int x, final int y) {
        final double range = 15;
        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }
    public boolean HitTarget () {
        if (this.v instanceof PlayerStatus) {
            return inRange((PlayerStatus) this.v, this.targX, this.targY);
        } else if (this.v instanceof Enemy) {
            return inRange((Enemy) this.v, this.targX, this.targY);
        } else {
            return inRange((Projectile) this.v, this.targX, this.targY);
        }
    }
    public boolean hitAvatar(GameObject o) {
        if (Aimer.getDist(this.v, o) < 64) {
            return true;
        }
        return false;
    }
}
