package com.example.hukin.Logic;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Levels {
    public static List<Enemy> level(int index, CharacterSprites enemy, Canvas canvas, PlayerStatus player) {
        levelEnemies n1 = new levelEnemies(0, 1, enemy, canvas, player);
        levelEnemies n2 = new levelEnemies(0, 3, enemy, canvas, player);
        levelEnemies n3 = new levelEnemies(0, 5, enemy, canvas, player);
        levelEnemies n4 = new levelEnemies(1, 1, enemy, canvas, player);
        levelEnemies n5 = new levelEnemies(1, 3, enemy, canvas, player);
        levelEnemies n6 = new levelEnemies(1, 5, enemy, canvas, player);
        levelEnemies n7 = new levelEnemies(2, 1, enemy, canvas, player);
        levelEnemies n8 = new levelEnemies(2, 3, enemy, canvas, player);
        levelEnemies n9 = new levelEnemies(2, 5, enemy, canvas, player);
        List<Enemy> enemies = new ArrayList<>();
        switch(index) {
            case 1:
                levelEnemies[] arr = {n1};
                enemies = WaveGenerator.wave(arr);
                break;
            case 2:
                levelEnemies[] arr2 = {n1, n1, n1};
                enemies = WaveGenerator.wave(arr2);
                break;
            case 3:
                levelEnemies[] arr3 = {n1, n4};
                enemies = WaveGenerator.wave(arr3);
                break;
            case 4:
                levelEnemies[] arr4 = {n1, n4, n4};
                enemies = WaveGenerator.wave(arr4);
                break;
            case 5:
                levelEnemies[] arr5 = {n2, n4, n7};
                enemies = WaveGenerator.wave(arr5);
                break;
            case 6:
                levelEnemies[] arr6 = {n4, n4, n7};
                enemies = WaveGenerator.wave(arr6);
                break;
            case 7:
                levelEnemies[] arr7 = {n7, n7, n2};
                enemies = WaveGenerator.wave(arr7);
                break;
            case 8:
                levelEnemies[] arr8 = {n2, n2, n5};
                enemies = WaveGenerator.wave(arr8);
                break;
            case 9:
                levelEnemies[] arr9 = {n2, n2, n3, n3};
                enemies = WaveGenerator.wave(arr9);
                break;
            case 10:
                levelEnemies[] arr10 = {n5, n5, n3};
                enemies = WaveGenerator.wave(arr10);
                break;
            case 11:
                levelEnemies[] arr11 = {n1, n1, n1, n1, n1, n1};
                enemies = WaveGenerator.wave(arr11);
                break;
            case 12:
                levelEnemies[] arr12 = {n4, n4, n9};
                enemies = WaveGenerator.wave(arr12);
                break;
            case 13:
                levelEnemies[] arr13 = {n2, n2, n2, n4, n4};
                enemies = WaveGenerator.wave(arr13);
                break;
            case 14:
                levelEnemies[] arr14 = {n5, n5, n8, n8};
                enemies = WaveGenerator.wave(arr14);
                break;
            case 15:
                levelEnemies[] arr15 = {n4, n4, n4, n4, n4, n4};
                enemies = WaveGenerator.wave(arr15);
                break;
            case 16:
                levelEnemies[] arr16 = {n9, n9, n9};
                enemies = WaveGenerator.wave(arr16);
                break;
            case 17:
                levelEnemies[] arr17 = {n6, n6, n6, n3, n3};
                enemies = WaveGenerator.wave(arr17);
                break;
            case 18:
                levelEnemies[] arr18 = {n1, n2, n3, n4, n5, n6, n7, n8};
                enemies = WaveGenerator.wave(arr18);
                break;
            case 19:
                levelEnemies[] arr19 = {n9, n9, n6, n6, n3, n3};
                enemies = WaveGenerator.wave(arr19);
                break;
            case 20:
                levelEnemies[] arr20 = {n3, n3, n3, n3, n3, n3, n3, n3};
                enemies = WaveGenerator.wave(arr20);
                break;
        }
        return enemies;

    }
}
