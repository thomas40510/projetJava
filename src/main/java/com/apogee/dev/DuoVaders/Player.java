package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Player extends Rectangle implements Ship {
    private int life;
    private int score;
    private double width;
    private double height;
    private Pane pane;
    @Override
    public void move(char dir, Scene s) {
        int dx = (dir == 'r') ? 10 : -10;
        double newPos = this.getX() + dx;

        if (newPos >= 0 && newPos <= s.getWidth() - this.width) {
            this.setX(newPos);
        }
    }

    @Override
    public void shoot() {

    }

    @Override
    public void die() {

    }

    @Override
    public int getHealth() {
        return 0;
    }

    public Player(double width, double height, Pane p) {
        super(width, height);
        this.height = height;
        this.width = width;
        this.life = 20;
        this.score = 0;
        this.pane = p;
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
