/**
 * helloFX.java
 * Fichier principal de l'application
 * @authors Margot Taillantou-Candau, Thomas Prévost
 * @version 1.8
 */

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
import java.util.concurrent.atomic.AtomicInteger;

public class DualVaders extends Application {

    static Stage primStage;

    /**
     * Méthode principale de l'application
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
        Text t = new Text("Menu");
        t.setFont(new Font(50));
        t.setX(s.getWidth() / 2 - t.getLayoutBounds().getWidth() / 2);
        t.setY(s.getHeight() / 2 - t.getLayoutBounds().getHeight() / 2);
        p.getChildren().add(t);
        //ajoute le bouton pour lancer le jeu
        JFXButton b = new JFXButton("Jouer");
        //set style of button
        b.setStyle("-fx-font-size: 20px;" +
                "-jfx-button-type: RAISED;" +
                "-fx-background-color: #cccccc");

        // Positionnement du bouton
        double bWidth = 80;
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
            Log.d("Quitting game");
            Platform.exit();
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
    static int[] playersScore = {0, 0};
    static List<Alien> enemies;
    static List<Rectangle> flyingBullets = new ArrayList<>();
    static Rectangle viePlayer1;
    static Rectangle viePlayer2;
    static Text lifeTxt1;
    static Text lifeTxt2;
    static Text scoreTxt1;
    static Text scoreTxt2;
    Text timerTxt;

    double timeElapsed = 0;

    int vie_max = 10;
    int nombre_ennemis = 25;
    int taille_ennemis = 40;

    static ArrayList<Player> players = new ArrayList<>();


    /**
     * Main game method - instantiate all the game objects, start game loops and handle mechanics
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    public void game(Stage primaryStage) {

        // créé la liste des carrés de 10*10 pixels de couleur rouge (enemy)
        enemies = new ArrayList<Alien>();


        // Create players

        //créé un panneau
        Pane p = new Pane();
        //créé une scène de 500x500 pixels
        Scene s = new Scene(p, 500, 500);


        Player r = new Player(50, 50, p, s, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        Player r2 = new Player(50, 50, p, s, KeyCode.A, KeyCode.D, KeyCode.E);
        players.add(r);
        players.add(r2);

        for (int i = 0; i < nombre_ennemis; i++) {
            Alien a = new Alien(taille_ennemis, taille_ennemis, p, s);
            enemies.add(a);
        }


        //ajoute les rectangles au panneau
        p.getChildren().addAll(enemies);

        //vie et score du vaisseau 2 en haut à gauche
        lifeTxt1 = new Text(356, 480, " Vie du vaisseau 1 : " + r.getHealth());
        p.getChildren().add(lifeTxt1);
        scoreTxt1 = new Text(400, 465, " Score 1 : " + playersScore[0]);
        p.getChildren().add(scoreTxt1);
        //ajoute la vie (rectangle) en haut à gauche de la fenêtre proportionnelle à la taille
        viePlayer1 = new Rectangle(10, r.getHealth() * 10, Color.CHARTREUSE);
        viePlayer1.setLayoutX(500 - viePlayer1.getWidth());
        viePlayer1.setLayoutY(500 - viePlayer1.getHeight());
        p.getChildren().add(viePlayer1);

        // vie et score du vaisseau 1 en bas à droite
        lifeTxt2 = new Text(10, 20, " Vie du vaisseau 2 : " + r2.getHealth());
        p.getChildren().add(lifeTxt2);
        scoreTxt2 = new Text(10, 35, " Score 2 : " + playersScore[1]);
        p.getChildren().add(scoreTxt2);
        viePlayer2 = new Rectangle(10, r2.getHealth() * 10, Color.CHARTREUSE);
        p.getChildren().add(viePlayer2);

        // Timer top right
        timerTxt = new Text(420, 20, " Temps : " + timeElapsed);
        p.getChildren().add(timerTxt);

        //place r au centre de la scène en bas
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
            checkWin(r, r2);
        });


        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();

        primStage = primaryStage; // save the primary stage for later use

        alienMove(taille_ennemis, s); // déplacement des aliens
        alienShoot(p, r, r2, s);  // tirs des aliens
        timer(); // timer

    }

    public void checkWin(Player r, Player r2){
        String msg = "";
        Log.d("Vie du vaisseau 1 : " + r.getHealth());
        Log.d("Vie du vaisseau 2 : " + r2.getHealth());
        if(r.getHealth() <= 0){
            Log.i("Over. Player 1 died");
            msg = "Player 1 died. Player 2 wins!";
        }
        if (r2.getHealth() <= 0){
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
     * Écran de fin de partie
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be primary stages.
     * @param msg Message à afficher aux joueurs (raison du game over)
     */
    public void gameOver(Stage primaryStage, String msg) {
        //crée un panneau
        Pane p = new Pane();
        //crée une scène
        Scene s = new Scene(p, 800, 600);
        //ajoute le texte de titre
        Text title = new Text("Game Over!");
        title.setFont(new Font(50));
        title.setX(s.getWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setY(s.getHeight() / 2 - title.getLayoutBounds().getHeight() / 2);
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
            Log.d("Quitting game");
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
     * Permet au timer de s'incrémenter toutes les 10 millisecondes
     */
    public void timer() {
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
     * Le déplacement vertical est aléatoire pour chaque alien.
     * @param taille_ennemis Taille des aliens
     * @param s Scène sur laquelle les aliens sont affichés
     */
    public void alienMove(int taille_ennemis, Scene s){
        int nb_enemies = enemies.size();
        //déplacement des ennemis
        //tableau des directions des aliens initié à 1 (droite)
        int[] directions = new int[nb_enemies];
        for (int i = 0; i < nb_enemies; i++){
            directions[i] = 1;
        }

        AtomicInteger bottomCount = new AtomicInteger();

        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1), e -> {
            Random rdm = new Random();
            int nombre_ennemis = enemies.size();
            for (int i = 0; i < nombre_ennemis; i++) {
                //si l'ennemi est à gauche de la fenêtre
                if (enemies.get(i).getX() <= 0) {
                    //on descend / monte et on change la direction
                    int random = rdm.nextInt(2);
                    int dY = (random == 0) ? -40 : 40;

                    bottomCount.getAndIncrement();
                    enemies.get(i).setX(enemies.get(i).getX() + 40);
                    enemies.get(i).setY(enemies.get(i).getY() + dY);
                    directions[i] = 1;
                }
                //si l'ennemi est à droite de la fenêtre
                if (enemies.get(i).getX() >= s.getWidth() - taille_ennemis) {
                    //déplace l'ennemi vers la gauche descend/monte d'une rangée et part vers la droite
                    // one chance on two to go down
                    int random = rdm.nextInt(2);
                    int dY = (random == 0) ? -40 : 40;
                    enemies.get(i).setX(enemies.get(i).getX() - 40);
                    enemies.get(i).setY(enemies.get(i).getY() + dY);
                    bottomCount.getAndIncrement();
                    directions[i] = -1;
                }
                //déplace l'ennemi
                enemies.get(i).setX(enemies.get(i).getX() + 10 * directions[i]);
                if (bottomCount.get() == 2*nb_enemies){
                    // on arrive à deux déplacements verticaux
                    timeline.stop();
                    Log.i("Stopping motion");
                }
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Déplacement des joueurs.
     * La seule condition est que les joueurs ne puissent pas sortir de l'écran.
     * @param dir Direction du déplacement ('r' pour droite, 'l' pour gauche)
     * @param r Rectangle représentant le joueur
     * @param s Scène sur laquelle le joueur est affiché
     */
//    public void move(Character dir, Rectangle r, Scene s) {
//        int dx = (dir == 'r') ? 10 : -10;
//        double newPos = r.getX() + dx;
//
//        if (newPos >= 0 && newPos <= s.getWidth() - r.getWidth()) {
//            r.setX(newPos);
//        }
//    }

    /**
     * Envoi de projectiles par les aliens.
     * Les aliens tirent aléatoirement, dans une direction aléatoire (haut ou bas).
     * @param p Panneau sur lequel les projectiles sont affichés
     * @param r Rectangle représentant le joueur 1
     * @param r2 Rectangle représentant le joueur 2
     * @param s Scène sur laquelle les projectiles sont affichés
     */
    public void alienShoot(Pane p, Rectangle r, Rectangle r2, Scene s){
        Random rdm = new Random();
        List<Rectangle> players = new ArrayList<Rectangle>(); // liste des joueurs
        players.add(r);
        players.add(r2);
        // every given time, random aliens shoot
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.4), e -> {
            int nombre_ennemis = enemies.size();
            int nb_aliens_shoot = rdm.nextInt(nombre_ennemis/2); // au maximum la moitié des aliens tirent

            // on prend nb_aliens_shoot ennemis au hasard dans la liste des aliens
            List<Integer> randoms = new ArrayList<Integer>();
            for (int i = 0; i < nb_aliens_shoot; i++){
                int random = rdm.nextInt(nombre_ennemis);
                while (randoms.contains(random)){
                    random = rdm.nextInt(nombre_ennemis);
                }
                randoms.add(random);
            }

            // on les fait tirer
            for (int i = 0; i < nb_aliens_shoot; i++){
                //shoot(3, p, enemies.get(randoms.get(i)), players);
                Log.d("Alien shoot");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * Action de tir. Contient également les mécaniques de collision et de fin de partie.
     * @param direction Direction du tir (sert d'option pour savoir qui a tiré)
     * @param p Pane sur laquelle le tir est affiché
     * @param shooter Rectangle représentant l'entité qui tire (joueur ou alien)
     * @param targets Liste des rectangles représentant les entités qui peuvent être touchées par le tir (joueurs ou aliens)
     */
//    public void shoot(int direction, Pane p, Rectangle shooter, List <Rectangle> targets) {
//        int dx;
//        int shootingShip = -1;
//        switch (direction) {
//            case 1: // tir du joueur 1
//                dx = -10;
//                shootingShip = 0;
//                break;
//            case 2: // tir du joueur 2
//                dx = 10;
//                shootingShip = 1;
//                break;
//            case 3: // tir d'un alien
//                Random rdm = new Random();
//                int n = rdm.nextInt(2);
//                dx = (n == 0) ? -10 : 10;
//                break;
//            default:
//                dx = 0;
//        }
//
//        // Création du projectile
//        Rectangle curr = new Bullet(1);
//        flyingBullets.add(curr);
//        p.getChildren().add(curr);
//        curr.setX(shooter.getX() + shooter.getWidth() / 2 - curr.getWidth() / 2);
//        curr.setY(shooter.getY() - curr.getHeight());
//
//        final Boolean[] running = {true};
//
//        //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
//        int finalDx = dx;
//        Timeline timeline = new Timeline();
//        int finalShootingShip = shootingShip;
//        KeyFrame kf = new KeyFrame(Duration.millis(50),
//                event -> {
//                    //ONLY the first enemy crossing the bullet is killed
//                    //any other enemy crossing the bullet is not killed
//                    if(direction != 3){
//                        for (int i = 0; i < targets.size(); i++) {
//                            Rectangle target = targets.get(i);
//                            if (curr.getBoundsInParent().intersects(target.getBoundsInParent())) {
//                                p.getChildren().remove(target);
//                                curr.setX(-1); // ensure no more hits
//                                p.getChildren().remove(curr);
//                                targets.remove(target);
//                                flyingBullets.remove(curr);
//                                playersScore[finalShootingShip]++;
//                                updateCounters();
//                                running[0] = false;
//                                break;
//                            }
//                        }
//                    } else {
//                        // shooter is an alien
//                        // if bullet hits a player, it looses life
//                        for (int i = 0; i < targets.size(); i++){
//                            Rectangle target = targets.get(i);
//                            if (curr.getBoundsInParent().intersects(target.getBoundsInParent())){
//                                playersLife[i]--;
//                                curr.setX(-1);
//                                p.getChildren().remove(curr);
//                                flyingBullets.remove(curr);
//                                Log.i("Vie du vaisseau "+(i+1)+" : "+playersLife[i]);
//                                updateCounters();
//                                running[0] = false;
//                                break;
//                            }
//                        }
//                    }
//                    // handle hit of another bullet
//                    for (Rectangle b : flyingBullets){
//                        if (curr.getBoundsInParent().intersects(b.getBoundsInParent()) && b != curr){
//                            Log.d("Bullet hit another bullet at position "+curr.getX()+","+curr.getY());
//                            curr.setX(-1);
//                            b.setX(-1);
//                            p.getChildren().remove(curr);
//                            p.getChildren().remove(b);
//                            flyingBullets.remove(b);
//                            flyingBullets.remove(curr);
//                            running[0] = false;
//                            timeline.stop();
//                            return;
//                        }
//                    }
//                    curr.setY(curr.getY() + finalDx);
//
//                    if (enemies.isEmpty()){ // if all aliens are dead
//                        Log.d("No enemies left");
//                        curr.setX(-1);
//                        running[0] = false;
//                        gameOver(primStage, "No enemies left. You win!");
//                        timeline.stop();
//                    }
//                    if (playersLife[0] <= 0 || playersLife[1] <= 0){ // if a player is dead
//                        String deadPlayer = (playersLife[0] <= 0) ? "Player 1" : "Player 2";
//                        String winner = (playersLife[0] <= 0) ? "Player 2" : "Player 1";
//                        Log.d(deadPlayer+" died.");
//                        curr.setX(-1);
//                        running[0] = false;
//                        gameOver(primStage, "No more lives left. "+deadPlayer+" is dead. "+winner+" wins!");
//                        timeline.stop();
//                    }
//                    if (curr.getY() < 0 || curr.getY() > p.getHeight()) {
//                        curr.setX(-1);
//                        running[0] = false;
//                        p.getChildren().remove(curr);
//                        flyingBullets.remove(curr);
//                        timeline.stop();
//                    }
//                });
//        timeline.getKeyFrames().add(kf);
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
//    }

    /**
     * Mise à jour des compteurs de score et de vie en cours de partie.
     */
    public static void updateCounters(){
        Platform.runLater(() -> {
            int lifeP1 = players.get(0).getHealth();
            int lifeP2 = players.get(1).getHealth();
            lifeTxt1.setText(" Vie du vaisseau 1 : " + lifeP1);
            lifeTxt2.setText(" Vie du vaisseau 2 : " + lifeP2);
            viePlayer1.setHeight(lifeP1*10);
            viePlayer2.setHeight(lifeP2*10);

            viePlayer1.setLayoutY(primStage.getHeight() - viePlayer1.getHeight() + 10);

            scoreTxt1.setText(" Score 1 : " + playersScore[0]);
            scoreTxt2.setText(" Score 2 : " + playersScore[1]);
        });
    }

    /**
     * Placement initial des ennemis
     *
     * @param nombre_enemies Nombre d'ennemis à placer
     * @param taille_enemies Taille des ennemis
     * @param s Scène sur laquelle les ennemis sont affichés
     */
    private void place_enemies(int nombre_enemies, int taille_enemies, Scene s){
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
        Log.d("Placed "+nombre_enemies+" enemies on "+nb_cols+" cols");
    }

    /**
     * Création d'un rectangle représentant un projectile
     * @return Rectangle de 5x5 représentant un projectile
     */
//    public Rectangle bullet(){
//        Rectangle b = new Rectangle(5, 5);
//        b.setFill(Color.BLUE);
//        return b;
//    }

    /**
     * Lancement du jeu
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}