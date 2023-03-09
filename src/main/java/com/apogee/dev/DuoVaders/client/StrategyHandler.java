package com.apogee.dev.DuoVaders.client;

/**
 * Interface donnant un cadre aux process à appliquer lors des actions d'un joueur local.
 * @version 1.0
 */
public interface StrategyHandler {
    public void shoot();
    public void move(char dir);

}
