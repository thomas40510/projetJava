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

    private int life;
    private int score;
    private final double width;
    private final double height;
    private final Pane pane;
    private final Scene scene;
    private final int canonType;
    private int shootDirection;
    private List<Bullet> bulletList;

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

    public PlayerRemote(double width, double height, Pane p, Scene s, List<Bullet> bulletList) {
        super(width, height);
        this.height = height;
        this.width = width;
        this.life = 20;
        this.score = 0;
        this.pane = p;
        this.scene = s;

        this.canonType = 1;
        this.bulletList = bulletList;

        setShootDirection();

        try {
            File playerImg = new File("src/main/resources/player.png");
            Image vaisseau = new Image(playerImg.toURI().toString());
            this.setFill(new ImagePattern(vaisseau, 0, 0, 1, 1, true));
        } catch (Exception e) {
            Log.e("Player", "Player image not found", e);
        }
        p.getChildren().add(this);
    }

}
