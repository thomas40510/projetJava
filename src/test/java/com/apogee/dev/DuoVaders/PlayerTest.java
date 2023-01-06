package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void move() {
        new Thread(() -> {
            // Check if player is moving
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            player.setX(0);
            player.setY(0);
            player.move('r', s);
            assertEquals(10, player.getX());
            assertEquals(0, player.getY());
            player.move('l', s);
            assertEquals(0, player.getX());
            assertEquals(0, player.getY());
            player.move('d', s);
            assertEquals(0, player.getX());
            assertEquals(10, player.getY());
            player.move('u', s);
            assertEquals(0, player.getX());
            assertEquals(0, player.getY());
        }).start();
    }

    @Test
    void shoot() {
        new Thread(() -> {
            List<Bullet> flyingBullets = new ArrayList<>();
            // Check if player is shooting
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE, flyingBullets);
            p.getChildren().add(player);

            for (int i = 0; i < 10; i++) {
                player.shoot();
                assertEquals(i + 1, flyingBullets.size());
            }
        }).start();
    }

    @Test
    void handleDamage() {
        new Thread(() -> {
            // Check if player is taking damage
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            for (int i = 0; i < 10; i++) {
                player.handleDamage();
                assertEquals(10 - i, player.getLife());
            }
        }).start();
    }

    @Test
    void getSize() {
        new Thread(() -> {
            // Check if player size is correct
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            int[] size = player.getSize();
            int w = size[0];
            int h = size[1];
            assertEquals(10, w);
            assertEquals(10, h);
        }).start();
    }

    @Test
    void getLife() {
        new Thread(() -> {
            // Check if player life is correct
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            assertEquals(10, player.getLife());
        }).start();
    }

    @Test
    void getKeyCode() {
        new Thread(() -> {
            // Check if player keycodes are correct
            Player player = new Player(10, 10, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            p.getChildren().add(player);

            assertEquals(KeyCode.LEFT, player.getKeyCode('l'));
            assertEquals(KeyCode.RIGHT, player.getKeyCode('r'));
            assertEquals(KeyCode.SPACE, player.getKeyCode('s'));
        }).start();
    }
}