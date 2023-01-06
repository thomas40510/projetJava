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

/**
 * Classe représentant un vaisseau alien.
 * @version 1.0
 * @see Ship
 * @see Rectangle
 */
public class Alien extends Rectangle implements Ship {
    private Pane pane;
    private Scene scene;
    private List<Bullet> bulletList;
    @Override
    public void move(char dir, Scene s) {
        int dx = 0;
        int dy = 0;
        switch (dir){
            case 'r':
                dx = 10;
                break;
            case 'l':
                dx = -10;
                break;
            case 'd':
                dy = 10;
                break;
            case 'u':
                dy = -10;
                break;
            default:
                dx = dy = 0;
                break;
        }
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    public void shoot() {
        Random rand = new Random();
        int direction = rand.nextInt(2);
        Bullet bullet = new Bullet(1, "red", this.pane);
        this.bulletList.add(bullet);
        this.pane.getChildren().add(bullet);
        bullet.setX(this.getX() + this.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY() + this.getHeight());
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(.1), e -> {
            if (bullet.getY() < 0 || bullet.getY() > this.scene.getHeight()) {
                timeline.stop();
                bullet.destroy();
            }
            for (Player target : DualVaders.players) {
                if (bullet.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    timeline.stop();
                    target.handleDamage();
                    bullet.destroy();
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
        DualVaders.checkEndGame();
    }

    /**
     * Constructeur de la classe Alien
     * @param width Largeur de l'Alien
     * @param height Hauteur de l'Alien
     * @param p Pane dans lequel l'Alien est affiché
     * @param s Scene dans laquelle l'Alien est affiché
     * @param bulletList Liste des balles en vol. Si non spécifié, la liste par défaut est utilisée.
     */
    public Alien(double width, double height, Pane p, Scene s, List<Bullet> bulletList) {
        this.setWidth(width);
        this.setHeight(height);
        this.pane = p;
        this.scene = s;
        this.bulletList = bulletList;
        try {
            File alienImg = new File("src/main/resources/alien.png");
            Image alien = new Image(alienImg.toURI().toString());
            this.setFill(new ImagePattern(alien));
        } catch (Exception e) {
            Log.e("Alien", "Error loading alien image", e);
            this.setFill(javafx.scene.paint.Color.RED);
        }
    }
    public Alien(double width, double height, Pane p, Scene s) {
        this.setWidth(width);
        this.setHeight(height);
        this.pane = p;
        this.scene = s;
        this.bulletList = DualVaders.flyingBullets;

        try {
            File alienImg = new File("src/main/resources/alien.png");
            Image alien = new Image(alienImg.toURI().toString());
            this.setFill(new ImagePattern(alien));
        } catch (Exception e) {
            //Log.e("Alien", "Error loading alien image", e);
            this.setFill(javafx.scene.paint.Color.RED);
        }
    }

}
