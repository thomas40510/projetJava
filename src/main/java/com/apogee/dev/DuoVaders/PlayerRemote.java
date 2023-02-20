package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

public class PlayerRemote extends Rectangle implements IPlayer{

    @Override
    public void move(char dir, Scene s) {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void handleDamage() {

    }

    @Override
    public void setLife(int life) {

    }

    @Override
    public int getLife() {
        return 0;
    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setShootDirection(int shootDirection) {

    }

    @Override
    public int getShootDirection() {
        return 0;
    }

    @Override
    public void setBulletList(List<Bullet> bulletList) {

    }

    @Override
    public List<Bullet> getBulletList() {
        return null;
    }
}
