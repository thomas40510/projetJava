package com.apogee.dev.DuoVaders;

import com.apogee.dev.DuoVaders.client.Bullet;
import com.apogee.dev.DuoVaders.client.PlayerLocal;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de la classe Player.
 * @version 1.0
 */
class PlayerTest {
    private Pane p;
    private Scene s;

    @BeforeEach
    void setUp() {
        new Thread(() -> {
            p = new Pane();
            s = new Scene(p, 800, 600);

            Stage primaryStage = new Stage();
            primaryStage.setScene(s);
            primaryStage.show();
        }).start();

    }

    /**
     * Test du déplacement du joueur.
     */
    @Test
    void move() {
        new Thread(() -> {
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            player.setX(0);
            player.setY(0);
            player.move('r', s);
            assertEquals(10, player.getX());
            assertEquals(0, player.getY());
            player.move('l', s);
            assertEquals(0, player.getX());
            assertEquals(0, player.getY());

            // Check if player cannot go out of the scene
            player.setX(0);
            player.move('l', s);
            assertEquals(0, player.getX());
        }).start();
    }

    /**
     * Test de la méthode shoot.
     */
    @Test
    void shoot() {
        new Thread(() -> {
            List<Bullet> flyingBullets = new ArrayList<>();
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE, flyingBullets);
            p.getChildren().add(player);

            for (int i = 0; i < 10; i++) {
                player.shoot();
                assertEquals(i + 1, flyingBullets.size());
            }
        }).start();
    }

    /**
     * Test de la prise de dégâts.
     */
    @Test
    void handleDamage() {
        new Thread(() -> {
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            for (int i = 0; i < 10; i++) {
                player.handleDamage();
                assertEquals(10 - i, player.getLife());
            }
        }).start();
    }

    /**
     * Test de l'accès à la taille du joueur.
     */
    @Test
    void getSize() {
        new Thread(() -> {
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            int[] size = player.getSize();
            int w = size[0];
            int h = size[1];
            assertEquals(10, w);
            assertEquals(10, h);
        }).start();
    }

    /**
     * Test d'accès à la vie du joueur.
     */
    @Test
    void getLife() {
        new Thread(() -> {
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            assertEquals(10, player.getLife());
        }).start();
    }

    /**
     * Test d'accès aux contrôles du joueur.
     */
    @Test
    void getKeyCode() {
        new Thread(() -> {
            PlayerLocal player = new PlayerLocal(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            assertEquals(KeyCode.LEFT, player.getKeyCode('l'));
            assertEquals(KeyCode.RIGHT, player.getKeyCode('r'));
            assertEquals(KeyCode.SPACE, player.getKeyCode('s'));
        }).start();
    }
}