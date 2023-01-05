package com.apogee.dev.DuoVaders;

import javafx.scene.shape.Rectangle;

/**
 * Classe représentant un projectile
 * @version 1.0
 * @see Rectangle
 */
public class Bullet extends Rectangle {

    /**
     * Constructeur de la classe Bullet
     * @param type Type de projectile. Plusieurs types ont été implémentés, un seul est utilisé pour le moment.
     * @param color Couleur du projectile
     */
    public Bullet(int type, String color) {
        super(5, 5);
        this.setStyle("-fx-fill: " + color + ";");
        switch(type){
            case 2:
                this.setWidth(2);
                this.setHeight(2);
                break;
            case 3:
                this.setWidth(10);
                this.setHeight(10);
                break;
            default:
                this.setWidth(5);
                this.setHeight(5);
                break;
        }
    }
}
