package com.apogee.dev.DuoVaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlienTest {
    Alien alien;

    @BeforeEach
    void setUp() {
        alien = new Alien(1, 100, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @Test
    void getHealth() {
        assertEquals(100, alien.getHealth());
    }

    @Test
    void getFireMode() {
        assertEquals(1, alien.getFireMode());
    }

    @Test
    void getSpeed() {
        assertEquals(1, alien.getSpeed());
    }

    @Test
    void getDamage() {
        assertEquals(1, alien.getDamage());
    }

    @Test
    void getShield() {
        assertEquals(1, alien.getShield());
    }

    @Test
    void getShieldRegen() {
        assertEquals(1, alien.getShieldRegen());
    }

    @Test
    void getShieldRegenDelay() {
        assertEquals(1, alien.getShieldRegenDelay());
    }

    @Test
    void getPosition() {
        assertEquals(1, alien.getPosition()[0]);
        assertEquals(1, alien.getPosition()[1]);
    }

    @Test
    void getType() {
        assertEquals(1, alien.getType());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setHealth(int a) {
        alien.setHealth(a);
        assertEquals(a, alien.getHealth());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    @DisplayName("Set Fire Mode")
    void setFireMode(int a) {
        alien.setFireMode(a);
        assertEquals(a, alien.getFireMode());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setSpeed(int a) {
        alien.setSpeed(a);
        assertEquals(a, alien.getSpeed());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setDamage(int a) {
        alien.setDamage(a);
        assertEquals(a, alien.getDamage());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setShield(int a) {
        alien.setShield(a);
        assertEquals(a, alien.getShield());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setShieldRegen(int a) {
        alien.setShieldRegen(a);
        assertEquals(a, alien.getShieldRegen());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    void setShieldRegenDelay(int a) {
        alien.setShieldRegenDelay(a);
        assertEquals(a, alien.getShieldRegenDelay());
    }
}