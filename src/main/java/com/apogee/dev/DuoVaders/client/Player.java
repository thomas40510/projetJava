package com.apogee.dev.DuoVaders.client;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Player extends Rectangle implements IPlayer, Observable {
    @Override
    public void move(char dir, Scene s) {

    }

    @Override
    public void shoot() {

    }

    @Override
    public void handleDamage() {

    }

    @Override
    public void setLife(int life) {

    }

    @Override
    public int getLife() {
        return 0;
    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setShootDirection(int shootDirection) {

    }

    @Override
    public int getShootDirection() {
        return 0;
    }

    @Override
    public void setBulletList(List<Bullet> bulletList) {

    }

    public KeyCode getKeyCode(char keyCode) {
        return null;
    }

    @Override
    public List<Bullet> getBulletList() {
        return null;
    }

    public Pane getPane() {
        return null;
    }

    public Player(double width, double height){
        super(width, height);
    }

    public int getcanonType() {
        return 0;
    }

    /**
     * @param invalidationListener
     */
    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    /**
     * @param invalidationListener
     */
    @Override
    public void removeListener(InvalidationListener invalidationListener) {
    }

}
