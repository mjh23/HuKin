package com.example.hukin.Logic;

public class WaveGenerator {
    public static Enemy[] wave(levelEnemies[] arr) {
        Enemy[] arr2 = new Enemy[arr.length];
        int length = GameArena.rightBound - GameArena.leftBound;
        int height = GameArena.bottomBound - GameArena.topBound;
        int n = arr.length / 4 + 1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j <= n; j++) {
                Enemy q = new Enemy(GameArena.leftBound - 64 + j * length / (n + 1),
                        GameArena.topBound +16, arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas);
                arr[i].sprite.draw(arr[i].canvas, q.getX(), q.getY());
                arr2[i] = q;
            }
            for (int j = 1; j <= n; j++) {
                Enemy q = new Enemy(GameArena.leftBound - 64 + j * length / (n + 1),
                        GameArena.bottomBound -128, arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas);
                arr[i].sprite.draw(arr[i].canvas, q.getX(), q.getY());
                arr2[i] = q;
            }
            for (int j = 1; j <= arr.length - 2*n; j++) {
                Enemy q = new Enemy(GameArena.leftBound + 64,GameArena.topBound + 64 + j * height / (n + 1),
                        arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas);
                arr[i].sprite.draw(arr[i].canvas, q.getX(), q.getY());
                arr2[i] = q;
            }
            for (int j = 1; j <= arr.length - 2*n; j++) {
                Enemy q = new Enemy(GameArena.rightBound - 64,GameArena.topBound + 64 + j * height / (n + 1),
                        arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas);
                arr[i].sprite.draw(arr[i].canvas, q.getX(), q.getY());
                arr2[i] = q;
            }
        }
        return arr2;
    }
}
