package com.apogee.dev.DuoVaders;

import javafx.scene.Scene;

/**
 * Interface représentant un vaisseau.
 * @version 1.0
 */
public interface Ship {
    /**
     * Déplacement des vaisseaux.
     * La seule condition est qu'ils ne puissent pas sortir de l'écran.
     * @param dir Direction du déplacement ('r' pour droite, 'l' pour gauche, 'u' pour haut, 'd' pour bas)
     * @param s Scène sur laquelle le joueur est affiché
     */
    public void move(char dir, Scene s);

    /**
     * Tir des vaisseaux. Cette fonction prend également en compte les collisions avec les vaisseaux ennemis.
     */
    public void shoot();

    /**
     * Prise en compte de la collision avec une balle ennemie.
     */
    public void handleDamage();
}
