package com.apogee.dev.DuoVaders.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

public class LocalStrategy implements StrategyHandler {
    private final Player player;
    private final Scene scene;
    private final Pane pane;

    public void shoot() {
                Bullet bullet = new Bullet(player.getcanonType(), "blue", this.pane);
        player.getBulletList().add(bullet);

        this.pane.getChildren().add(bullet);
        bullet.setX(player.getX() + player.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(player.getY());

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
                    player.setScore(player.getScore() + 1);
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


            bullet.setY(bullet.getY() + this.player.getShootDirection());
        });

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    public void move() {
    }

    public LocalStrategy(Player player) {
        this.player = player;
        this.scene = player.getScene();
        this.pane = player.getPane();
    }
}
