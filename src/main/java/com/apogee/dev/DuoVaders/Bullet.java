package com.apogee.dev.DuoVaders;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Classe représentant un projectile
 * @version 1.0
 * @see Rectangle
 */
public class Bullet extends Rectangle {
    private final Pane pane;

    /**
     * Destruction du projectile. Concrètement, on le supprime du Pane et de la liste des projectiles en vol.
     */
    public void destroy() {
        DualVaders.flyingBullets.remove(this);
        this.pane.getChildren().remove(this);
    }

    /**
     * Constructeur de la classe Bullet
     * @param type Type de projectile. Plusieurs types ont été implémentés, un seul est utilisé pour le moment.
     * @param color Couleur du projectile
     */
    public Bullet(int type, String color, Pane pane) {
        super(5, 5);
        this.setStyle("-fx-fill: " + color + ";");
        this.pane = pane;
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
