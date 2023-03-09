package com.apogee.dev.DuoVaders.client;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;

/**
 * Classe représentant un joueur local. Ses coups sont récupérés par le clavier et transformés en actions sur le jeu local.
 * L'envoi ou non des informations au serveur est décidée par la stratégie appliquée.
 * @version 1.0
 * @see Ship
 * @see Rectangle
 */
public class PlayerLocal extends Player {
    private int life;
    private int score;
    private final double width;
    private final double height;
    private final Pane pane;
    private final Scene scene;
    private final KeyCode left, right, shoot;
    private final int canonType;
    private int shootDirection;
    private List<Bullet> bulletList;

    private final StrategyHandler strategyHandler;

    @Override
    public void move(char dir, Scene s) {
        strategyHandler.move(dir);
    }

    @Override
    public void shoot() {
        strategyHandler.shoot();
    }

    @Override
    public void setLife(int life) {

    }

    @Override
    public void handleDamage() {
        this.life--;
        DualVaders.updateCounters();
        DualVaders.checkEndGame();
    }

    /**
     * Accès à la taille du joueur
     * @return taille du joueur, de la forme [largeur, hauteur]
     */
    public int[] getSize() {
        return new int[]{(int) this.width, (int) this.height};
    }

    /**
     * Accès au score du joueur
     * @return score du joueur
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public void setShootDirection(int shootDirection) {

    }

    @Override
    public int getShootDirection() {
        return shootDirection;
    }

    @Override
    public List<Bullet> getBulletList() {
        return null;
    }

    @Override
    public void setBulletList(List<Bullet> bulletList) {

    }

    public int getLife() {
        return this.life;
    }

    @Override
    public void setScore(int score) {

    }

    /**
     * Obtention d'une touche de déplacement du joueur.
     * @param key Instruction ('l' pour gauche, 'r' pour droite, 's' pour tirer)
     * @return La touche associée à l'instruction.
     */
    public KeyCode getKeyCode(char key) {
        switch (key) {
            case 'l':
                return left;
            case 'r':
                return right;
            case 's':
                return shoot;
            default:
                return null;
        }
    }

    /**
     * Direction de tir du joueur en fonction de sa touche d'instruction de tir.
     * Si la touche est 'E', le joueur est en haut de l'écran et tire vers le bas.
     * Si la touche est 'SPACE', le joueur est en bas de l'écran et tire vers le haut.
     */
    private void setShootDirection(){
        switch (this.getKeyCode('s')){
            case E:
                this.shootDirection = 10;
                break;
            case SPACE:
                this.shootDirection = -10;
                break;
            default:
                this.shootDirection = 0;
        }
    }

    public Pane getPane() {
        return pane;
    }


    /**
     * Constructeur du joueur.
     * @param width Largeur du joueur.
     * @param height Hauteur du joueur.
     * @param p Pane dans lequel le joueur est affiché.
     * @param s Scene dans laquelle le joueur est affiché.
     * @param left Touche de déplacement gauche.
     * @param right Touche de déplacement droite.
     * @param shoot Touche de tir.
     * @param bulletList Liste des projectiles en tirés. Si non précisé, la liste est celle de DualVaders.
     */
    public PlayerLocal(double width, double height, Pane p, Scene s,
                       KeyCode left, KeyCode right, KeyCode shoot,
                       List<Bullet> bulletList, boolean isLocal) {
        super(width, height);
        this.height = height;
        this.width = width;
        this.life = 20;
        this.score = 0;
        this.pane = p;
        this.scene = s;
        this.left = left;
        this.right = right;
        this.shoot = shoot;
        this.canonType = 1;
        this.bulletList = bulletList;
        this.strategyHandler = isLocal ? new LocalStrategy(this) : new RemoteStrategy(this);

        setShootDirection();

        try {
            File playerImg = new File("src/main/resources/player.png");
            Image vaisseau = new Image(playerImg.toURI().toString());
            this.setFill(new ImagePattern(vaisseau, 0, 0, 1, 1, true));
        } catch (Exception e) {
            Log.e("Player", "Player image not found", e);
        }
        p.getChildren().add(this);
    }
    public PlayerLocal(double width, double height, Pane p, Scene s, KeyCode left, KeyCode right, KeyCode shoot, boolean isLocal) {
        this(width, height, p, s, left, right, shoot, DualVaders.flyingBullets, isLocal);
    }
    public PlayerLocal(double width, double height, Pane p, Scene s, KeyCode left, KeyCode right, KeyCode shoot, List<Bullet> bulletList) {
        this(width, height, p, s, left, right, shoot, bulletList, true);
    }
    public PlayerLocal(double width, double height, Pane p, Scene s, KeyCode left, KeyCode right, KeyCode shoot) {
        this(width, height, p, s, left, right, shoot, DualVaders.flyingBullets, true);
    }
}
