package com.apogee.dev.DuoVaders.client;

import javafx.scene.Scene;

import java.util.List;

public interface IPlayer extends Ship {

    /**
     * Déplacement du joueur
     * @param dir direction du déplacement
     * @param s scène du jeu
     */
    void move(char dir, Scene s);

    /**
     * Tir du joueur
     */
    void shoot();

    /**
     * Met la vie du joueur à une valeur donnée
     * @param life nouvelle valeur de la vie du joueur
     */
    void setLife(int life);

    /**
     * Accès à la vie du joueur
     * @return vie du joueur
     */
    int getLife();

    /**
     * Met le score du joueur à une valeur donnée
     * @param score
     */
    void setScore(int score);

    /**
     * Accès au score du joueur
     * @return score du joueur
     */
    int getScore();

    /**
     * Met la direction de tir du joueur à une valeur donnée.
     * Cette valeur est utilisée comme coefficient multiplicateur pour la vitesse du tir.
     * @param shootDirection nouvelle valeur de la direction de tir du joueur
     * @see com.apogee.dev.DuoVaders.client.Bullet
     */
    void setShootDirection(int shootDirection);

    /**
     * Accès à la direction de tir du joueur
     * @return direction de tir du joueur
     */
    int getShootDirection();

    /**
     * Place une liste donnée comme liste de balles du joueur
     * @param bulletList liste de ballles du joueur
     */
    void setBulletList(List<Bullet> bulletList);

    /**
     * Accès à la liste de balles du joueur
     * @return liste de balles du joueur
     */
    List<Bullet> getBulletList();

}
