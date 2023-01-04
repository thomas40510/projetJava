package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

public class Alien extends Rectangle implements Ship {
    private Pane pane;
    private Scene scene;
    @Override
    public void move(char dir, Scene s) {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void die() {
        this.pane.getChildren().remove(this);
        DualVaders.enemies.remove(this);
    }

    @Override
    public int getHealth() {
        return 0;
    }

    public Alien(double width, double height, Pane p, Scene s) {
        this.setWidth(width);
        this.setHeight(height);
        this.pane = p;
        this.scene = s;

        try {
            File alienImg = new File("src/main/resources/alien.png");
            Image alien = new Image(alienImg.toURI().toString());
            this.setFill(new ImagePattern(alien));
        } catch (Exception e) {
            Log.e("Alien", "Error loading alien image", e);
        }

    }
}
