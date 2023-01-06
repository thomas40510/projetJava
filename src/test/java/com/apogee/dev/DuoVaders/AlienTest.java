package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlienTest {

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
        // Check if aliens are moving
        Alien alien = new Alien(10, 10, p, s);
        p.getChildren().add(alien);

        alien.setX(0);
        alien.setY(0);
        alien.move('r', s);
        assertEquals(10, alien.getX());
        assertEquals(0, alien.getY());
        alien.move('l', s);
        assertEquals(0, alien.getX());
        assertEquals(0, alien.getY());
        alien.move('d', s);
        assertEquals(0, alien.getX());
        assertEquals(10, alien.getY());
        alien.move('u', s);
        assertEquals(0, alien.getX());
        assertEquals(0, alien.getY());
    }

    @Test
    void shoot() {
        new Thread(() -> {
            List<Bullet> flyingBullets = new ArrayList<>();
            // Check if aliens are shooting
            Alien alien = new Alien(10, 10, p, s, flyingBullets);
            p.getChildren().add(alien);

            alien.setX(alien.getWidth());
            alien.setY(alien.getHeight());
            for (int i = 0; i < 10; i++) {
                alien.shoot();
                assertEquals(i+1, flyingBullets.size());
            }
        }).start();
    }

    @Test
    void handleDamage() {
        new Thread(() -> {
            List<Alien> aliens = new ArrayList<>();
            // Check if aliens are taking damage
            for (int i = 0; i < 10; i++) {
                Alien alien = new Alien(10, 10, p, s);
                p.getChildren().add(alien);
                aliens.add(alien);
            }

            for (Alien alien : aliens) {
                alien.handleDamage();
                // check if still in pane
                assertFalse(p.getChildren().contains(alien));
            }

        }).start();
    }
}