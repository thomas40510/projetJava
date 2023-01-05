package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;

public interface Ship {

    public void move(char dir, Scene s);

    public void shoot();
    public void handleDamage();
    public int getHealth();
}
