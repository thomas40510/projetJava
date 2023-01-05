package com.apogee.dev.DuoVaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.security.Key;
import java.util.List;
import java.util.Random;

public class Alien extends Rectangle implements Ship {
    private Pane pane;
    private Scene scene;
    @Override
    public void move(char dir, Scene s) {

    }

    public void shoot() {
        Random rand = new Random();
        int direction = rand.nextInt(2);
        Bullet bullet = new Bullet(1, "red");
        DualVaders.flyingBullets.add(bullet);
        this.pane.getChildren().add(bullet);
        bullet.setX(this.getX() + this.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY() + this.getHeight());
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(.1), e -> {
            if (bullet.getY() < 0 || bullet.getY() > this.scene.getHeight()) {
                timeline.stop();
                DualVaders.flyingBullets.remove(bullet);
                this.pane.getChildren().remove(bullet);
            }
            for (Player target : DualVaders.players) {
                if (bullet.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    timeline.stop();
                    target.handleDamage();
                    DualVaders.flyingBullets.remove(bullet);
                    this.pane.getChildren().remove(bullet);
                }
            }
            int dx = (direction == 0) ? 10 : -10;
            bullet.setY(bullet.getY() + dx);
        });
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }


    @Override
    public void handleDamage() {
        this.pane.getChildren().remove(this);
        DualVaders.enemies.remove(this);
    }

    @Override
    public int getLife() {
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
