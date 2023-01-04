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

public class Player extends Rectangle implements Ship {
    private int life;
    private int score;
    private double width;
    private double height;
    private Pane pane;
    private Scene scene;
    private KeyCode left, right, shoot;
    private int canonType;
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
        Rectangle bullet = new Bullet(this.canonType);
        DualVaders.flyingBullets.add(bullet);

        this.pane.getChildren().add(bullet);
        bullet.setX(this.getX() + this.width / 2 - bullet.getWidth() / 2);
        bullet.setY(this.getY() - bullet.getHeight());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        List<Alien> targets = DualVaders.enemies;

        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(0.01), e -> {
            bullet.setY(bullet.getY() - 10);
            if (bullet.getY() < 0 || bullet.getY() > this.scene.getHeight()) {
                timeline.stop();
                DualVaders.flyingBullets.remove(bullet);
                this.pane.getChildren().remove(bullet);
            }
            for (int i = 0; i < targets.size(); i++){
                if (bullet.getBoundsInParent().intersects(targets.get(i).getBoundsInParent())) {
                    targets.get(i).die();
                    this.score++;
                    DualVaders.flyingBullets.remove(bullet);
                    this.pane.getChildren().remove(bullet);
                    DualVaders.updateCounters();
                    timeline.stop();
                    break;
                }
            }

        });

        //        KeyFrame kf = new KeyFrame(Duration.millis(50),
//                event -> {
//                    //ONLY the first enemy crossing the bullet is killed
//                    //any other enemy crossing the bullet is not killed
//                    if(direction != 3){
//                        for (int i = 0; i < targets.size(); i++) {
//                            Rectangle target = targets.get(i);
//                            if (curr.getBoundsInParent().intersects(target.getBoundsInParent())) {
//                                p.getChildren().remove(target);
//                                curr.setX(-1); // ensure no more hits
//                                p.getChildren().remove(curr);
//                                targets.remove(target);
//                                flyingBullets.remove(curr);
//                                playersScore[finalShootingShip]++;
//                                updateCounters();
//                                running[0] = false;
//                                break;
//                            }


    }

    @Override
    public void die() {

    }

    @Override
    public int getHealth() {
        return 0;
    }

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
