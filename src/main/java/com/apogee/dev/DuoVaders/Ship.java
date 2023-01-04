package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;

import java.util.List;

public interface Ship {

    public void move(char dir, Scene s);

    public void shoot();
    public void die();
    public int getHealth();
}
