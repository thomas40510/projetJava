package com.apogee.dev.DuoVaders.client;

import javafx.scene.Scene;

import java.util.List;

public interface IPlayer extends Ship {

    void move(char dir, Scene s);
    void shoot();
    void setLife(int life);
    int getLife();
    void setScore(int score);
    int getScore();
    void setShootDirection(int shootDirection);
    int getShootDirection();
    void setBulletList(List<Bullet> bulletList);
    List<Bullet> getBulletList();

}
