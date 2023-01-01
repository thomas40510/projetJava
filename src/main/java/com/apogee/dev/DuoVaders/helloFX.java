//charge la classe principale com.apogee.dev.DuoVaders.MainActivity

package com.apogee.dev.DuoVaders;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
import java.util.*;

//import le module pour nombres aléatoires
import java.util.Random;



//importe le module de gestion des images
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class helloFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        //valeur vie
        int vie1 = 20;
        int nombre_ennemis = 35;
        int taille_ennemis = 50;
        // créé la liste des carrés de 10*10 pixels de couleur rouge (enemy)
        List<Rectangle> enemies = new ArrayList<Rectangle>();
        for (int i = 0; i < nombre_ennemis; i++) {
            Rectangle enemy = new Rectangle(taille_ennemis, taille_ennemis, Color.RED);
            enemies.add(enemy);
        }
        Image alien = new Image("https://static.vecteezy.com/ti/vecteur-libre/p3/3134697-dessin-illustration-du-vaisseau-spatial-gratuit-vectoriel.jpg");
        //donne l'image alien aux ennemis
        for (int i = 0; i < nombre_ennemis; i++) {
            enemies.get(i).setFill(new ImagePattern(alien));
        }
        //créé un rectangle de la taille de images\vaisseau.png (player)
        Rectangle r = new Rectangle(50, 50);
        //ce reclangle est l'image du vaisseau images\vaisseau.png
        Image vaisseau = new Image("https://freepngimg.com/thumb/categories/1873.png");
        r.setFill(new ImagePattern( vaisseau , 0, 0, 1, 1, true));
        //créé un deuxième rectangle de 50x50 pixels
        Rectangle r2 = new Rectangle(50, 50);
        r2.setFill(new ImagePattern( vaisseau , 0, 0, 1, 1, true));
        //créé un panneau
        Pane p = new Pane();
        //ajoute les rectangles au panneau
        p.getChildren().add(r);
        p.getChildren().add(r2);
        for (int i = 0; i < nombre_ennemis; i++) {
            p.getChildren().add(enemies.get(i));
        }
        //ajoute la vie au panneau (texte)
        p.getChildren().add(new javafx.scene.text.Text(10, 20, " Vie du vaisseau 1 : " + vie1));
        //ajoute la vie (rectangle) en haut à gauche de la fenêtre proportionnelle à la taille
        p.getChildren().add(new Rectangle(10, vie1 * 10, Color.CHARTREUSE));
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
    }

    public void move(Character dir, Rectangle r, Scene s) {
        int dx = (dir == 'r') ? 10 : -10;
        double newPos = r.getX() + dx;

        if (newPos >= 0 && newPos <= s.getWidth() - r.getWidth()) {
            r.setX(newPos);
        }
    }

    public void shoot(int direction, Pane p, Rectangle r, List <Rectangle> enemies) {
        int dx = (direction == 1) ? -10 : 10;
        Rectangle curr = bullet();
        p.getChildren().add(curr);
        curr.setX(r.getX() + r.getWidth() / 2 - curr.getWidth() / 2);
        curr.setY(r.getY() - curr.getHeight());

        curr.setX(r.getX() + r.getWidth() / 2 - curr.getWidth() / 2);

        boolean condition = (direction == 1) ? curr.getY() > 0 : curr.getY() < 500;

        //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (condition) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //ONLY the first enemy crossing the bullet is killed
                            //any other enemy crossing the bullet is not killed
                            for (int i = 0; i < enemies.size(); i++) {
                                if (curr.getBoundsInParent().intersects(enemies.get(i).getBoundsInParent())) {
                                    p.getChildren().remove(enemies.get(i));
                                    curr.setX(-1); // ensure no more hits
                                    p.getChildren().remove(curr);
                                    enemies.remove(enemies.get(i));
                                    break;
                                }
                            }
                            curr.setY(curr.getY() + dx);
                        }
                    });
                }
            }
        }).start();
    }

    private void place_ennemies(List<Rectangle> enemies, int nombre_enemies, int taille_enemies, Scene s){
        /*
        Set placement of enemies mid-X and mid-Y, by rows of 4
        The enemies set is absolutely centered on the scene
         */
        int nb_cols = 9;
        int nb_rows = nombre_enemies/nb_cols;
        int x_offset = (int) ((s.getWidth() - nb_cols*taille_enemies)/2);
        int y_offset = (int) ((s.getHeight() - nb_rows*taille_enemies)/2);
        for (int i = 0; i < nombre_enemies; i++) {
            int x = x_offset + (i%nb_cols)*taille_enemies;
            int y = y_offset + (i/nb_cols)*taille_enemies;
            enemies.get(i).setX(x);
            enemies.get(i).setY(y);
        }
    }

    public Rectangle bullet(){
        Rectangle b = new Rectangle(5, 5);
        b.setFill(Color.BLUE);
        return b;
    }



    public static void main(String[] args) {
        launch(args);
    }
}