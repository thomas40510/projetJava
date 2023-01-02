//charge la classe principale com.apogee.dev.DuoVaders.MainActivity

package com.apogee.dev.DuoVaders;

import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class helloFX extends Application {

    Stage primStage;

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
        //Button b = new Button("Jouer");
        // centered on layout
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
            //create blurring pane with spinner on top
            game(primaryStage);
        });

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

        //set focus on text
        t.requestFocus();

        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();
    }

    double[] playersLife = new double[2];
    List<Rectangle> enemies;
    List<Rectangle> flyingBullets = new ArrayList<>();

    public void game(Stage primaryStage) {
        //valeur vie
        int vie_max = 20;
        int nombre_ennemis = 15;
        int taille_ennemis = 40;
        playersLife = new double[]{vie_max, vie_max};
        // créé la liste des carrés de 10*10 pixels de couleur rouge (enemy)
        enemies = new ArrayList<Rectangle>();
        for (int i = 0; i < nombre_ennemis; i++) {
            Rectangle enemy = new Rectangle(taille_ennemis, taille_ennemis, Color.RED);
            enemies.add(enemy);
        }
        try {
            // load resources/alien.png
            File alienImg = new File("src/main/resources/alien.png");
            Image alien = new Image(alienImg.toURI().toString());

            //donne l'image alien aux ennemis
            for (int i = 0; i < nombre_ennemis; i++) {
                enemies.get(i).setFill(new ImagePattern(alien));
            }
        } catch (Exception e){
            Log.e("images", "Error on setting alien Image", e);
            Platform.exit();
        }
        //créé un rectangle de la taille de images\vaisseau.png (player)
        Rectangle r = new Rectangle(50, 50);
        Rectangle r2 = new Rectangle(50, 50);

        //ce reclangle est l'image du vaisseau images\vaisseau.png
        try {
            File playerImg = new File("src/main/resources/player.png");
            Image vaisseau = new Image(playerImg.toURI().toString());

            r.setFill(new ImagePattern(vaisseau, 0, 0, 1, 1, true));
            //créé un deuxième rectangle de 50x50 pixels
            r2.setFill(new ImagePattern(vaisseau, 0, 0, 1, 1, true));
            // r2 rotate by 180
            r2.setStyle("-fx-rotate: 180");
        } catch (Exception e){
            Log.e("images", "Error on setting player Image", e);
        }
        //créé un panneau
        Pane p = new Pane();
        //ajoute les rectangles au panneau
        p.getChildren().add(r);
        p.getChildren().add(r2);
        for (int i = 0; i < nombre_ennemis; i++) {
            p.getChildren().add(enemies.get(i));
        }
        //ajoute la vie au panneau (texte)
        p.getChildren().add(new javafx.scene.text.Text(10, 20, " Vie du vaisseau 1 : " + playersLife[0]));
        //ajoute la vie (rectangle) en haut à gauche de la fenêtre proportionnelle à la taille
        p.getChildren().add(new Rectangle(10, playersLife[0] * 10, Color.CHARTREUSE));
        //créé une scène de 500x500 pixels
        Scene s = new Scene(p, 500, 500);
        //place r au centre de la scène en bas
        r.setX(s.getWidth() / 2 - r.getWidth() / 2);
        r.setY(s.getHeight() - r.getHeight());
        //place r2 au centre de la scène en haut
        r2.setX(s.getWidth() / 2 - r2.getWidth() / 2);
        r2.setY(0);
        // placement des ennemis
        place_ennemies(enemies, nombre_ennemis, taille_ennemis, s);



        //ajoute un gestionnaire d'événements pour les touches du clavier
        s.setOnKeyPressed(e -> {
            //si la touche est la flèche de droite
            if (e.getCode() == KeyCode.RIGHT) {
                //déplace le rectangle de 10 pixels vers la droite
                move('r', r, s);
            }
            //si la touche est la flèche de gauche
            if (e.getCode() == KeyCode.LEFT) {
                //déplace le rectangle de 10 pixels vers la gauche
                move('l', r, s);
            }
            //si la touche est la touhce q
            if (e.getCode() == KeyCode.Q) {
                //déplace le rectangle de 10 pixels vers la gauche
                move('l', r2, s);
            }
            //si la touche est la touche d
            if (e.getCode() == KeyCode.D) {
                //déplace le rectangle de 10 pixels vers la droite
                move('r', r2, s);
            }
            //si la touche est la touche espace, ajoute un projectile au niveau du vaisseau r
            if (e.getCode() == KeyCode.SPACE) {
                shoot(1, p, r, enemies);
            }
            // si la touche est la touche e, ajoute un projectile au niveau du vaisseau r2
            if (e.getCode() == KeyCode.E) {
                shoot(2, p, r2, enemies);
            }
        });


        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();

        primStage = primaryStage;

        alien_move (taille_ennemis, s);
        alienShoot(p, r, r2, s);

    }

    public void gameOver(Stage primaryStage, String msg) {
        //crée un panneau
        Pane p = new Pane();
        //crée une scène
        Scene s = new Scene(p, 800, 600);
        //ajoute le texte du menu
        Text title = new Text("Game over!");
        title.setFont(new Font(50));
        title.setX(s.getWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setY(s.getHeight() / 2 - title.getLayoutBounds().getHeight() / 2);
        p.getChildren().add(title);
        Text m = new Text(msg);
        m.setFont(new Font(18));
        m.setStyle("-fx-color-label-visible: #4d4d4d");
        //place below title
        m.setX(s.getWidth() / 2 - m.getLayoutBounds().getWidth() / 2);
        m.setY(s.getHeight() / 2 - m.getLayoutBounds().getHeight() / 2 + 50);

        //ajoute le bouton pour lancer le jeu
        JFXButton b = new JFXButton("Rejouer");
        //set style of button
        b.setStyle("-fx-font-size: 20px;-jfx-button-type: RAISED;-fx-background-color: #cccccc");
        //Button b = new Button("Jouer");
        // centered on layout
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

    public void alienShoot(Pane p, Rectangle r, Rectangle r2, Scene s){
        Random rdm = new Random();
        List<Rectangle> players = new ArrayList<Rectangle>();
        players.add(r);
        players.add(r2);
        // every random time, random aliens shoot
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.4), e -> {
            if (playersLife[0] == 0 || playersLife[1] == 0 || enemies.size() == 0){
                return;
            }
            int nombre_ennemis = enemies.size();
            int nb_aliens_shoot = rdm.nextInt(nombre_ennemis/4);
            // list of nb_aliens_shoot random values
            List<Integer> randoms = new ArrayList<Integer>();
            for (int i = 0; i < nb_aliens_shoot; i++){
                int random = rdm.nextInt(nombre_ennemis);
                while (randoms.contains(random)){
                    random = rdm.nextInt(nombre_ennemis);
                }
                randoms.add(random);
            }
            for (int i = 0; i < nb_aliens_shoot; i++){
                shoot(3, p, enemies.get(randoms.get(i)), players);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //deplacement aliens
    /*
     déplacement des ennemis (L'armada alien verticale part de la gauche de l'écran et se déplace horizontalement de gauche à droite.
     Lorsqu'elle atteint le côté droit de l'écran, elle descend d'une rangée et se déplace de droite à gauche.
     Lorsqu'elle atteint le côté gauche de l'écran, elle descend d'une rangée et se déplace à nouveau vers la droite.
    */


    public void alien_move (int taille_ennemis, Scene s){
        int nb_enemies = enemies.size();
        //déplacement des ennemis
        //tableau des directions des aliens initié à 1 (droite)
        int[] directions = new int[nb_enemies];
        for (int i = 0; i < nb_enemies; i++){
            directions[i] = 1;
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            int nombre_ennemis = enemies.size();
            for (int i = 0; i < nombre_ennemis; i++) {
                //si l'ennemi est en bas de la fenêtre
                if (enemies.get(i).getY() >= s.getHeight() - taille_ennemis) {
                    //le jeu est perdu
                    //System.out.println("Game Over");
                }
                //si l'ennemi est en haut de la fenêtre
                if (enemies.get(i).getY() <= 0) {
                    //le jeu est perdu
                    //System.out.println("Game Over");
                }
                //si l'ennemi est à gauche de la fenêtre
                if (enemies.get(i).getX() <= 0) {
                    enemies.get(i).setX(enemies.get(i).getX() + 40);
                    enemies.get(i).setY(enemies.get(i).getY() - 40);
                    //pause pendant 10 msecondes
                    /*try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                       */
                    //change la direction de l'ennemi
                    directions[i] = 1;
                }
                //si l'ennemi est à droite de la fenêtre
                if (enemies.get(i).getX() >= s.getWidth() - taille_ennemis) {
                    //déplace l'ennemi vers la gauche descend d'une rangée et part vers la droite
                    enemies.get(i).setX(enemies.get(i).getX() - 40);
                    enemies.get(i).setY(enemies.get(i).getY() - 40);
                    //pause pendant 10 msecondes
                    /*try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                       */
                    //change la direction de l'ennemi
                    directions[i] = -1;
                }
                //déplace l'ennemi
                enemies.get(i).setX(enemies.get(i).getX() + 10 * directions[i]);

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void move(Character dir, Rectangle r, Scene s) {
        int dx = (dir == 'r') ? 10 : -10;
        double newPos = r.getX() + dx;

        if (newPos >= 0 && newPos <= s.getWidth() - r.getWidth()) {
            r.setX(newPos);
        }
    }

    public void shoot(int direction, Pane p, Rectangle shooter, List <Rectangle> targets) {
        int dx;
        switch (direction) {
            case 1 -> dx = -10;
            case 2 -> dx = 10;
            case 3 -> {
                Random rdm = new Random();
                int n = rdm.nextInt(2);
                dx = (n == 0) ? -10 : 10;
            }
            default -> dx = 0;
        }
        Rectangle curr = bullet();
        flyingBullets.add(curr);
        p.getChildren().add(curr);
        curr.setX(shooter.getX() + shooter.getWidth() / 2 - curr.getWidth() / 2);
        curr.setY(shooter.getY() - curr.getHeight());

        curr.setX(shooter.getX() + shooter.getWidth() / 2 - curr.getWidth() / 2);

        final boolean[] conditions = {(direction == 1) ? curr.getY() > 0 : curr.getY() < 500};
        final Boolean[] running = {true};

        //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
        int finalDx = dx;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (curr.getY() > 0 && curr.getY() < 500 && curr.getX() != -1 && running[0]) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e1) {
                        Log.e(null,"Error while shooting", e1);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //ONLY the first enemy crossing the bullet is killed
                            //any other enemy crossing the bullet is not killed
                            if(direction != 3){
                                for (int i = 0; i < targets.size(); i++) {
                                    Rectangle target = targets.get(i);
                                    if (curr.getBoundsInParent().intersects(target.getBoundsInParent())) {
                                        p.getChildren().remove(target);
                                        curr.setX(-1); // ensure no more hits
                                        p.getChildren().remove(curr);
                                        targets.remove(target);
                                        flyingBullets.remove(curr);
                                        running[0] = false;
                                        break;
                                    }
                                }
                            } else {
                                // shooter is an alien
                                // if bullet hits a player, it looses life
                                for (int i = 0; i < targets.size(); i++){
                                    Rectangle target = targets.get(i);
                                    if (curr.getBoundsInParent().intersects(target.getBoundsInParent())){
                                        playersLife[i]--;
                                        curr.setX(-1);
                                        p.getChildren().remove(curr);
                                        flyingBullets.remove(curr);
                                        Log.i("Vie du vaisseau "+(i+1)+" : "+playersLife[i]);
                                        running[0] = false;
                                        break;
                                    }
                                }
                            }
                            // handle hit of another bullet
                            for (Rectangle b : flyingBullets){
                                if (curr.getBoundsInParent().intersects(b.getBoundsInParent()) && b != curr){
                                    Log.d("Bullet hit another bullet at position "+curr.getX()+","+curr.getY());
                                    curr.setX(-1);
                                    b.setX(-1);
                                    p.getChildren().remove(curr);
                                    p.getChildren().remove(b);
                                    flyingBullets.remove(b);
                                    flyingBullets.remove(curr);
                                    running[0] = false;
                                    return;
                                }
                            }
                            curr.setY(curr.getY() + finalDx);

                            if (enemies.isEmpty()){
                                Log.d("No enemies left");
                                curr.setX(-1);
                                running[0] = false;
                                gameOver(primStage, "No enemies left. You win!");
                            }

                        }
                    });
                }
            }
        }).start();
    }

    private void place_ennemies(List<Rectangle> enemies, int nombre_enemies, int taille_enemies, Scene s){
        /*
        Set placement of enemies mid-X and mid-Y, by rows of 4
        The enemies set is on the left of the scene
         */
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
        Log.d("Placed "+nombre_enemies+" enemies on "+nb_cols+" and "+nb_rows+" rows");
    }

    public Rectangle bullet(){
        Rectangle b = new Rectangle(5, 5);
        b.setFill(Color.BLUE);
        return b;
    }

    //le main lance la page home
    public static void main(String[] args) {
        launch(args);
    }
}