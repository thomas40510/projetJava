package com.apogee.dev.DuoVaders.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.List;

/**
 * Classe implémentant les méthodes de l'interface StrategyHandler pour le jeu en local.
 * @version 1.0
 * @see StrategyHandler
 */
public class LocalStrategy implements StrategyHandler {
    private final Player player;

    public void shoot() {
        Bullet bullet = new Bullet(player.getcanonType(), "blue", player.getPane());
        DualVaders.flyingBullets.add(bullet);

        player.getPane().getChildren().add(bullet);
        bullet.setX(player.getX() + player.getWidth() / 2 - bullet.getWidth() / 2);
        bullet.setY(player.getY());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        List<Alien> targets = DualVaders.enemies;

        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(0.01), e -> {
            if (bullet.getY() < 0 || bullet.getY() > player.getScene().getHeight()) {
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


            bullet.setY(bullet.getY() + player.getShootDirection());
        });

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void move(char dir) {
        int dx = (dir == 'r') ? 10 : -10;
        double newPos = player.getX() + dx;

        if (newPos >= 0 && newPos <= player.getScene().getWidth() - player.getWidth()) {
            player.setX(newPos);
        }

    }

    public LocalStrategy(Player player) {
        this.player = player;
    }
}
