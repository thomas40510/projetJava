package com.apogee.dev.DuoVaders;

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

/**
 * Classe représentant un joueur
 * @version 1.0
 * @see Ship
 * @see Rectangle
 */

public class Player extends Rectangle implements Ship {
    private int life;
    private int score;
    private final double width;
    private final double height;
    private final Pane pane;
    private final Scene scene;
    private final KeyCode left, right, shoot;
    private final int canonType;
    private int shootDirection;
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
        Bullet bullet = new Bullet(this.canonType, "blue", this.pane);
        DualVaders.flyingBullets.add(bullet);

        this.pane.getChildren().add(bullet);
        bullet.setX(this.getX() + this.width / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        List<Alien> targets = DualVaders.enemies;

        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(0.01), e -> {
            if (bullet.getY() < 0 || bullet.getY() > this.scene.getHeight()) {
                timeline.stop();
                bullet.destroy();
            }
            for (Alien target : targets) {
                if (bullet.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    target.handleDamage();
                    this.score++;
                    bullet.destroy();
                    DualVaders.updateCounters();
                    timeline.stop();
                    break;
                }
            }
            // collision with other bullets
            for (Bullet otherBullet : DualVaders.flyingBullets) {
                if (bullet.getBoundsInParent().intersects(otherBullet.getBoundsInParent()) && otherBullet != bullet) {
                    timeline.stop();
                    bullet.destroy();
                    otherBullet.destroy();
                    break;
                }
            }


            bullet.setY(bullet.getY() + this.shootDirection);
        });

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @Override
    public void handleDamage() {
        this.life--;
        DualVaders.updateCounters();
        DualVaders.checkEndGame();
    }

    /**
     * Accès à la taille du joueur
     * @return taille du joueur, de la forme [largeur, hauteur]
     */
    public int[] getSize() {
        return new int[]{(int) this.width, (int) this.height};
    }

    /**
     * Accès au score du joueur
     * @return score du joueur
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Accès à la vie du joueur.
     * @return La vie du joueur.
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Obtention d'une touche de déplacement du joueur.
     * @param key Instruction ('l' pour gauche, 'r' pour droite, 's' pour tirer)
     * @return La touche associée à l'instruction.
     */
    public KeyCode getKeyCode(char key) {
        switch (key) {
            case 'l':
                return left;
            case 'r':
                return right;
            case 's':
                return shoot;
            default:
                return null;
        }
    }

    /**
     * Direction de tir du joueur en fonction de sa touche d'instruction de tir.
     * Si la touche est 'E', le joueur est en haut de l'écran et tire vers le bas.
     * Si la touche est 'SPACE', le joueur est en bas de l'écran et tire vers le haut.
     */
    private void setShootDirection(){
        switch (this.getKeyCode('s')){
            case E:
                this.shootDirection = 10;
                break;
            case SPACE:
                this.shootDirection = -10;
                break;
            default:
                this.shootDirection = 0;
        }
    }

    /**
     * Constructeur du joueur.
     * @param width Largeur du joueur.
     * @param height Hauteur du joueur.
     * @param p Pane dans lequel le joueur est affiché.
     * @param s Scene dans laquelle le joueur est affiché.
     * @param left Touche de déplacement gauche.
     * @param right Touche de déplacement droite.
     * @param shoot Touche de tir.
     */
    public Player(double width, double height, Pane p, Scene s, KeyCode left, KeyCode right, KeyCode shoot) {
        super(width, height);
        this.height = height;
        this.width = width;
        this.life = 20;
        this.score = 0;
        this.pane = p;
        this.scene = s;
        this.left = left;
        this.right = right;
        this.shoot = shoot;
        this.canonType = 1;

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
