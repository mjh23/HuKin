package com.example.hukin.Logic;

import java.util.ArrayList;
import java.util.List;

public class WaveGenerator {
    public static List<Enemy> wave(levelEnemies[] arr) {
        List<Enemy> l2 = new ArrayList<>();
        int length = GameArena.rightBound - GameArena.leftBound;
        int height = GameArena.bottomBound - GameArena.topBound;
        int n = arr.length / 4 + 1;
        int i = 0;
        for (int j = 1; j <= n; j++) {
            if (i < arr.length) {
                Enemy q = new Enemy(GameArena.leftBound - 64 + j * length / (n + 1),
                        GameArena.topBound + 128, arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas, arr[i].playTarg);
                l2.add(q);
                i++;
            }
        }
        for (int j = 1; j <= n; j++) {
            if (i < arr.length) {
                Enemy q = new Enemy(GameArena.leftBound - 64 + j * length / (n + 1),
                        GameArena.bottomBound -128, arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas, arr[i].playTarg);
                l2.add(q);
                i++;
            }
        }
        for (int j = 1; j <= arr.length - 2*n; j++) {
            if (i < arr.length) {
                Enemy q = new Enemy(GameArena.leftBound + 64,GameArena.topBound + 64 + j * height / (n + 1),
                        arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas, arr[i].playTarg);
                l2.add(q);
                i++;
            }
        }
        for (int j = 1; j <= arr.length - 2*n; j++) {
            if (i < arr.length) {
                Enemy q = new Enemy(GameArena.rightBound - 64,GameArena.topBound + 64 + j * height / (n + 1),
                        arr[i].sprite, arr[i].role, arr[i].level, arr[i].canvas, arr[i].playTarg);
                l2.add(q);
                i++;
            }
        }

        return l2;
    }
}
