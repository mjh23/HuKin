package com.example.hukin.Logic;

import android.graphics.Canvas;

import java.util.List;

public class Moveable extends GameObject {
    protected double speed;
    protected int targX;
    protected int targY;
    Moveable(final int x, final int y, final CharacterSprites setSprite, final double setSpeed, final Canvas setCanvas) {
        super(x, y, setSprite, setCanvas);
        speed = setSpeed;
        targX = x;
        targY = y;
    }
    Moveable(final int x, final int y, final CharacterSprites setSprite, final double setSpeed, final Canvas setCanvas, PlayerStatus player) {
        super(x, y, setSprite, setCanvas);
        speed = setSpeed;
        targX = player.x;
        targY = player.y;
    }
    Moveable(final int x, final int y, final double setSpeed, final Canvas setCanvas, Moveable p) {
        super(x, y, setCanvas);
        speed = setSpeed;
        if (p == null) {
            targX = x;
            targY = y;
        } else {
            targX = p.x;
            targY = p.y;
        }
    }

    public void setTarg(final int newX, final int newY) {
        targX = newX;
        targY = newY;
    }
    protected void move2() {
        int x1 = x;
        int y1 = y;
        double distance = Math.sqrt((x1-targX)*(x1-targX)+(y1-targY)*(y1-targY));
        double Velocity;
        if (this instanceof PlayerStatus) {
            PlayerStatus z = (PlayerStatus) this;
            Velocity = speed * 3;
            if (inRange(z, targX, targY)) {
                return;
            }
        } else if (this instanceof Enemy) {
            Velocity = speed * 3;
            Enemy z = (Enemy) this;
            if (inRange(z, targX, targY)) {
                return;
            }
        } else {
            Velocity = speed * 1;
            Projectile p = (Projectile) this;
            if (inRange(p, targX, targY)) {
                return;
            }
        }

        double time = distance/Velocity;
        double xSpeed = (x1-targX)/time;
        double ySpeed = (y1-targY)/time;

        this.setX((int) (x1 - xSpeed));
        this.setY((int) (y1 - ySpeed));
    }

    public static void massMove (final List<Projectile> arrPE,final List<Projectile> arrPP, final List<Enemy> arrE, final PlayerStatus p) {
        for (Moveable q : arrPE) {
            q.move2();
        }
        if (arrPP != null || arrPP.size() != 0) {
            for (Moveable q : arrPP) {
                q.move2();
            }
        }
        for (Moveable q : arrE) {
            q.move2();
        }
        p.move2();
    }




    private static boolean inRange(final Moveable q, final int x, final int y) {
        final double range;
        if (q instanceof PlayerStatus) {
            range = 10;
        } else if (q instanceof Enemy) {
            Enemy e = (Enemy) q;
            range = e.getRange()/1.5;
        } else {
            range = 15;
        }

        final double distance = Math.sqrt((q.getX() - x)*(q.getX() - x) + (q.getY() - y) * (q.getY() - y));
        return distance <= range;
    }

    public boolean HitTarget () {
        if (this instanceof PlayerStatus) {
            return inRange((PlayerStatus) this, this.targX, this.targY);
        } else if (this instanceof Enemy) {
            return inRange((Enemy) this, this.targX, this.targY);
        } else {
            return inRange((Projectile) this, this.targX, this.targY);
        }
    }
    public boolean hitAvatar(GameObject o) {
        if (Projectile.getDist(this, o) < 64) {
            return true;
        }
        return false;
    }
}

