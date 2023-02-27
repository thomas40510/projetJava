package com.apogee.dev.DuoVaders;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe principale du jeu DualVaders
 * @author Margot Taillantou-Candau, Thomas Prévost
 * @version 2.0
 * @see Application
 */

public class DualVaders extends Application {
    static Stage primStage;

    /**
     * Menu principal de l'application
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    public void start (Stage primaryStage) {
        //crée un panneau
        Pane p = new Pane();
        //crée une scène
        Scene s = new Scene(p, 800, 600);
        //ajoute le texte du menu
        Text t = title("Menu", s);
        //place le texte au centre en haut de la scene
        t.setX(s.getWidth() / 2 - t.getLayoutBounds().getWidth() / 2);
        t.setY(s.getHeight() / 4 - t.getLayoutBounds().getHeight() / 2);
        p.getChildren().add(t);
        //ajoute le bouton pour lancer le jeu
        JFXButton b = new JFXButton("Jouer en local");
        //set style of button
        b.setStyle("-fx-font-size: 20px;" +
                "-jfx-button-type: RAISED;" +
                "-fx-background-color: #cccccc");

        // Positionnement du bouton
        double bWidth = 200;
        double bHeight = 40;
        b.setMinSize(bWidth, bHeight);
        b.setMaxSize(bWidth, bHeight);

        b.setLayoutX(s.getWidth() / 2 - bWidth / 2);
        b.setLayoutY(s.getHeight() / 2 - bWidth / 2 + 50);
        p.getChildren().add(b);
        //ajoute un gestionnaire d'événements pour le bouton
        b.setOnAction(e -> {
            //lance le jeu
            game(primaryStage);
        });

        // Ajout d'un bouton pour quitter le jeu
        JFXButton q = new JFXButton("Quit");
        q.setStyle("-fx-font-size: 20px;-jfx-button-type: RAISED;-fx-background-color: #cccccc");
        q.setMinSize(bWidth, bHeight);
        q.setMaxSize(2*bWidth, 2*bHeight);
        q.setLayoutX(s.getWidth() / 2 - bWidth / 2);
        q.setLayoutY(s.getHeight() / 2 + bWidth / 1.3);
        p.getChildren().add(q);

        q.setOnAction(e -> {
            // exit game
            Log.i("Quitting game");
            Platform.exit();
        });

        //ajoute un bouton pour jouer en reseau
        JFXButton b2 = new JFXButton("Jouer en réseau");
        b2.setStyle("-fx-font-size: 20px;-jfx-button-type: RAISED;-fx-background-color: #cccccc");
        b2.setMinSize(bWidth, bHeight);
        b2.setMaxSize(bWidth, bHeight);
        b2.setLayoutX(s.getWidth() / 2 - bWidth / 2);
        b2.setLayoutY(s.getHeight() / 2 - bWidth / 2 + 100);
        p.getChildren().add(b2);
        //ajoute un gestionnaire d'événements pour le bouton
        b2.setOnAction(e -> {
            //lance le jeu
            game(primaryStage);
        });

        //set focus on text (better render of buttons)
        t.requestFocus();

        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();
    }

    /**********************
     * Variables globales *
     **********************/
    static List<Alien> enemies;
    static List<Bullet> flyingBullets = new ArrayList<>();
    static Rectangle viePlayer1;
    static Rectangle viePlayer2;
    static Text lifeTxt1;
    static Text lifeTxt2;
    static Text scoreTxt1;
    static Text scoreTxt2;
    static Text timerTxt;

    static double timeElapsed = 0;

    static int nombre_ennemis = 25;
    static int taille_ennemis = 40;

    static ArrayList<Player> players = new ArrayList<>();


    /**
     * Main game method - instantiate all the game objects, start game loops and handle mechanics
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    public static void game(Stage primaryStage) {

        // initialisation de la liste des ennemis
        enemies = new ArrayList<>();

        //créé un panneau
        Pane p = new Pane();
        //créé une scène de 500x500 pixels
        Scene s = new Scene(p, 500, 500);


        // Joueurs
        Player r = new Player(50, 50, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        Player r2 = new Player(50, 50, p, s, KeyCode.A, KeyCode.D, KeyCode.E);
        players.add(r);
        players.add(r2);

        // Aliens
        for (int i = 0; i < nombre_ennemis; i++) {
            Alien a = new Alien(taille_ennemis, taille_ennemis, p, s);
            enemies.add(a);
        }


        //ajoute les rectangles au panneau
        p.getChildren().addAll(enemies);

        //vie et score du vaisseau 2 en haut à gauche
        lifeTxt1 = new Text(356, 480, " Vie du vaisseau 1 : " + r.getLife());
        p.getChildren().add(lifeTxt1);
        scoreTxt1 = new Text(400, 465, " Score 1 : " + r.getScore());
        p.getChildren().add(scoreTxt1);
        //ajoute la vie (rectangle) en haut à gauche de la fenêtre proportionnelle à la taille
        viePlayer1 = new Rectangle(10, r.getLife() * 10, Color.CHARTREUSE);
        viePlayer1.setLayoutX(500 - viePlayer1.getWidth());
        viePlayer1.setLayoutY(500 - viePlayer1.getHeight());
        p.getChildren().add(viePlayer1);

        // vie et score du vaisseau 1 en bas à droite
        lifeTxt2 = new Text(10, 20, " Vie du vaisseau 2 : " + r2.getLife());
        p.getChildren().add(lifeTxt2);
        scoreTxt2 = new Text(10, 35, " Score 2 : " + r2.getScore());
        p.getChildren().add(scoreTxt2);
        viePlayer2 = new Rectangle(10, r2.getLife() * 10, Color.CHARTREUSE);
        p.getChildren().add(viePlayer2);

        // Timer top right
        timerTxt = new Text(420, 20, " Temps : " + timeElapsed);
        p.getChildren().add(timerTxt);

        //place le joueur 1 au centre de la scène en bas
        r.setX(s.getWidth() / 2 - r.getWidth() / 2);
        r.setY(s.getHeight() - r.getHeight());
        //place r2 au centre de la scène en haut
        r2.setX(s.getWidth() / 2 - r2.getWidth() / 2);
        r2.setY(0);
        r2.rotateProperty().set(180);
        // placement des ennemis
        place_enemies(nombre_ennemis, taille_ennemis, s);

        //ajoute un gestionnaire d'événements pour les touches du clavier
        s.setOnKeyPressed(e -> {
            for(Player player : players){
                if (e.getCode() == player.getKeyCode('l')) {
                    player.move('l', s);
                } else if (e.getCode() == player.getKeyCode('r')) {
                    player.move('r', s);
                } else if (e.getCode() == player.getKeyCode('s')) {
                    player.shoot();
                }
            }
        });


        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();

        primStage = primaryStage; // save the primary stage for later use

        alienMove(s); // déplacement des aliens
        alienShoot();  // tirs des aliens
        timer(); // timer

    }

    /**
     * Texte de titre
     * @param text texte à afficher
     * @param s scène sur laquelle afficher le texte
     * @return le texte mis en forme
     */
    private static Text title(String text, Scene s) {
        Text t = new Text(text);
        t.setFont(new Font(50));
        t.setX(s.getWidth() / 2 - t.getLayoutBounds().getWidth() / 2);
        t.setY(s.getHeight() / 2 - t.getLayoutBounds().getHeight() / 2);
        return t;
    }


    /**
     * Écran de fin de partie
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be primary stages.
     * @param msg Message à afficher aux joueurs (raison du game over)
     */
    public static void gameOver(Stage primaryStage, String msg) {
        //crée un panneau
        Pane p = new Pane();
        //crée une scène
        Scene s = new Scene(p, 800, 600);
        //ajoute le texte de titre
        Text title = title("Game Over!", s);
        p.getChildren().add(title);

        // message
        Text m = new Text(msg + " Temps de jeu : " + timeElapsed + "s");
        m.setFont(new Font(18));
        m.setStyle("-fx-color-label-visible: #acacac");
        m.setX(s.getWidth() / 2 - m.getLayoutBounds().getWidth() / 2);
        m.setY(s.getHeight() / 2 - m.getLayoutBounds().getHeight() / 2 + 10);
        p.getChildren().add(m);

        //ajoute le bouton pour relancer le jeu
        JFXButton b = new JFXButton("Rejouer");
        b.setStyle("-fx-font-size: 20px;-jfx-button-type: RAISED;-fx-background-color: #cccccc");
        double bWidth = 100;
        double bHeight = 40;
        b.setMinSize(bWidth, bHeight);
        b.setMaxSize(bWidth, bHeight);

        b.setLayoutX(s.getWidth() / 2 - bWidth / 2);
        b.setLayoutY(s.getHeight() / 2 - bWidth / 2 + 100);
        p.getChildren().add(b);
        //ajoute un gestionnaire d'événements pour le bouton
        b.setOnAction(e -> {
            //lance le jeu
            game(primaryStage);
        });

        // Bouton pour quitter
        JFXButton q = new JFXButton("Quit");
        q.setStyle("-fx-font-size: 20px;-jfx-button-type: RAISED;-fx-background-color: #cccccc");
        q.setMinSize(bWidth, bHeight);
        q.setMaxSize(2*bWidth, 2*bHeight);
        q.setLayoutX(s.getWidth() / 2 - bWidth / 2);
        q.setLayoutY(s.getHeight() / 2 + bWidth / 1.3 + 30);
        p.getChildren().add(q);

        q.setOnAction(e -> {
            // exit game
            Log.i("Quitting game");
            Platform.exit();
        });

        //set focus on text
        title.requestFocus();

        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();
    }

    /**
     * Vérification de la fin du jeu (gagnant ou perdant)
     */
    public static void checkEndGame(){
        String msg = "";
        Player r = players.get(0);
        Player r2 = players.get(1);
        if(r.getLife() <= 0){
            Log.i("Over. Player 1 died");
            msg = "Player 1 died. Player 2 wins!";
        }
        if (r2.getLife() <= 0){
            Log.i("Over. Player 2 died");
            msg = "Player 2 died. Player 1 wins!";
        }
        if (enemies.size() == 0){
            Log.i("Over. All aliens died");
            msg = "All aliens died. Players win!";
        }
        if (!msg.equals("")) gameOver(primStage, msg);
    }


    /**
     * Permet au timer de s'incrémenter toutes les 10 millisecondes
     */
    public static void timer() {
        // Timer
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), ev -> {
            timeElapsed += 0.01;
            // round to 2 decimals
            timeElapsed = Math.round(timeElapsed * 100.0) / 100.0;
            timerTxt.setText(" Temps : " + timeElapsed);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Déplacement des aliens.
     * L'armada alien verticale part de la gauche de l'écran et se déplace horizontalement de gauche à droite.
     * Lorsqu'elle atteint le côté droit de l'écran, elle monte ou descend d'une rangée et se déplace de droite à gauche.
     * Lorsqu'elle atteint le côté gauche de l'écran, elle monte ou descend d'une rangée et se déplace à nouveau vers la droite.
     * Le déplacement vertical est aléatoire pour l'ensemble de la flotte.
     * @param s la scène du jeu
     */
    public static void alienMove(Scene s){
        Timeline timeline = new Timeline();
        AtomicInteger changeCount = new AtomicInteger();
        AtomicBoolean changedDir = new AtomicBoolean(false);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), e -> {
            Random rdm = new Random();
            if (!changedDir.get() && (enemies.stream().anyMatch(enemy -> enemy.getX() >= s.getWidth() - enemy.getWidth()) ||
                    enemies.stream().anyMatch(enemy -> enemy.getX() <= enemy.getWidth()))) {
                /* Aliens are at the edge of the screen, go up or down */
                int r = rdm.nextInt(2);
                for (Alien alien : enemies){
                    char dir = (r == 0) ? 'u' : 'd';
                    alien.move(dir, s);
                }
                changeCount.getAndIncrement();
                changedDir.set(true);
            } else { // move horizontally depending on the current position
                char dir;
                if (changeCount.get() % 2 == 0) {
                    dir = 'l';
                } else {
                    dir = 'r';
                }
                for (Alien alien : enemies){
                    alien.move(dir, s);
                }
                changedDir.set(false);
            }

        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * Élection des aliens qui vont tirer.
     * Cette sélection est aléatoire dans [0, nb_aliens/2[.
     */
    public static void alienShoot(){
        Random rdm = new Random();
        Platform.runLater(() -> {
            Timeline timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.seconds(3), e -> {
                if (enemies.size() == 0 || players.get(0).getLife() == 0 || players.get(1).getLife() == 0){
                    timeline.stop();
                }
                try {
                    int nombre_ennemis = enemies.size();
                    int nb_aliens_shooting = rdm.nextInt(nombre_ennemis / 2);
                    List<Integer> aliens_shooting = new ArrayList<>();
                    for (int i = 0; i < nb_aliens_shooting; i++) {
                        int alien_nbr;
                        do {
                            alien_nbr = rdm.nextInt(nombre_ennemis);
                        } while (aliens_shooting.contains(alien_nbr));
                        aliens_shooting.add(alien_nbr);
                    }

                    for (int i = 0; i < nb_aliens_shooting; i++) {
                        enemies.get(i).shoot();
                    }
                } catch (Exception ex) {
                    Log.e("Aliens", "Error while shooting", ex);
                    timeline.stop();
                }
            });
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    /**
     * Mise à jour des compteurs de score et de vie en cours de partie.
     */
    public static void updateCounters(){
        Platform.runLater(() -> {
            // Life counters
            int lifeP1 = players.get(0).getLife();
            int lifeP2 = players.get(1).getLife();
            lifeTxt1.setText(" Vie du vaisseau 1 : " + lifeP1);
            lifeTxt2.setText(" Vie du vaisseau 2 : " + lifeP2);
            viePlayer1.setHeight(lifeP1*10);
            viePlayer2.setHeight(lifeP2*10);

            viePlayer1.setLayoutY(primStage.getHeight() - viePlayer1.getHeight() + 10);

            // Score counters
            int scoreP1 = players.get(0).getScore();
            int scoreP2 = players.get(1).getScore();
            scoreTxt1.setText(" Score 1 : " + scoreP1);
            scoreTxt2.setText(" Score 2 : " + scoreP2);
        });
    }

    /**
     * Placement initial des ennemis.
     * @param nombre_enemies Nombre d'ennemis à placer
     * @param taille_enemies Taille des ennemis
     * @param s Scène sur laquelle les ennemis sont affichés
     */
    private static void place_enemies(int nombre_enemies, int taille_enemies, Scene s){
        int nb_cols = 9;
        int nb_rows = nombre_enemies/nb_cols;
        int x_offset = (int) ((s.getWidth() - nb_cols*taille_enemies)/2);
        int y_offset = (int) ((s.getHeight() - nb_rows*taille_enemies)/2);
        for (int i = 0; i < nombre_enemies; i++) {
            int x = x_offset+(i%nb_cols)*taille_enemies;
            int y = y_offset + (i/nb_cols)*taille_enemies;
            enemies.get(i).setX(x);
            enemies.get(i).setY(y);
        }
        Log.i("Placed "+nombre_enemies+" enemies on "+nb_cols+" cols");
    }

    /**
     * Lancement du jeu
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}