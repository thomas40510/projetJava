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
        int nombre_ennemis = 5;
        int taille_ennemis = 50;
        // créé la liste des carrés de 10*10 pixels de couleur rouge (enemy)
        List<Rectangle> enemies = new ArrayList<Rectangle>();
        for (int i = 0; i <= nombre_ennemis; i++) {
            Rectangle enemy = new Rectangle(taille_ennemis, taille_ennemis, Color.RED);
            enemies.add(enemy);
        }
        Image alien = new Image("https://static.vecteezy.com/ti/vecteur-libre/p3/3134697-dessin-illustration-du-vaisseau-spatial-gratuit-vectoriel.jpg");
        //donne l'image alien aux ennemis
        for (int i = 0; i <= nombre_ennemis; i++) {
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
        for (int i = 0; i <= nombre_ennemis; i++) {
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
        //place les ennemis au centre de la scène (todo : à gerer avec une fonction/non fonctionnel à plus de 9 ennemis/à optimiser)
        for (int i = 0; i <= nombre_ennemis; i++) {
            if (nombre_ennemis > 9) {
                if (i<nombre_ennemis/4) {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 - taille_ennemis * i);
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2 + 100 );
                }
                if (i>=nombre_ennemis/4 && i<nombre_ennemis/2) {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 - taille_ennemis * (i-nombre_ennemis/4));
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2 + 100);
                }
                if (i>=nombre_ennemis/2 && i<nombre_ennemis*3/4) {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 - taille_ennemis *(i/2));
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2 - 100);
                }
                else {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 + taille_ennemis * ((i/2)-nombre_ennemis/4));
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2 - 100);
                }
            }
            else {
                if (i<nombre_ennemis/2) {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 - taille_ennemis * (i+1));
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2 );
                }
                else {
                    enemies.get(i).setX(s.getWidth() / 2 - enemies.get(i).getWidth() / 2 + taille_ennemis * (i-nombre_ennemis/2));
                    enemies.get(i).setY(s.getHeight() / 2 - enemies.get(i).getHeight() / 2);
                }
            }
        }
        //ajoute un gestionnaire d'événements pour les touches du clavier
        s.setOnKeyPressed(e -> {
            //si la touche est la flèche de droite
            if (e.getCode() == KeyCode.RIGHT) {
                //déplace le rectangle de 10 pixels vers la droite
                r.setX(r.getX() + 10);
            }
            //si la touche est la flèche de gauche
            if (e.getCode() == KeyCode.LEFT) {
                //déplace le rectangle de 10 pixels vers la gauche
                r.setX(r.getX() - 10);
            }
            //si la touche est la touhce q
            if (e.getCode() == KeyCode.Q) {
                //déplace le rectangle de 10 pixels vers la gauche
                r2.setX(r2.getX() - 10);
            }
            //si la touche est la touche d
            if (e.getCode() == KeyCode.D) {
                //déplace le rectangle de 10 pixels vers la droite
                r2.setX(r2.getX() + 10);
            }
            //si la touche est la touche espace, ajoute un projectile au niveau du vaisseau r
            if (e.getCode() == KeyCode.SPACE) {
                //ajoute le rectangle b au panneau
                Rectangle curr = bullet();
                p.getChildren().add(curr);
                curr.setX(r.getX() + r.getWidth() / 2 - curr.getWidth() / 2);
                curr.setY(r.getY() - curr.getHeight());

                curr.setX(r.getX() + r.getWidth() / 2 - curr.getWidth() / 2);
                //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (curr.getY() > 0) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(50);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // handle collisions
                                    for (int i = 0; i < enemies.size(); i++) {
                                        Rectangle enm = enemies.get(i);
                                        if (curr.getBoundsInParent().intersects(enm.getBoundsInParent()) && p.getChildren().contains(enm)) {
                                            p.getChildren().remove(enm);
                                            p.getChildren().remove(curr);
                                            enemies.remove(i);
                                        }
                                    }
                                    curr.setY(curr.getY() - 10);

                                }
                            });
                        }
                    }
                }).start();
            }
            // si la touche est la touche e, ajoute un projectile au niveau du vaisseau r2
            if (e.getCode() == KeyCode.E) {

                Rectangle curr2 = bullet();
                p.getChildren().add(curr2);
                curr2.setX(r2.getX() + r2.getWidth() / 2 - curr2.getWidth() / 2);
                curr2.setY(r2.getY() - curr2.getHeight());

                curr2.setX(r2.getX() + r2.getWidth() / 2 - curr2.getWidth() / 2);

                //déplace le rectangle de 10 pixels vers le bas automatiquement toutes les 0.5s
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //while inferieur à la taille de la fenetre
                        while (curr2.getY() < s.getHeight() ) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(50);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    curr2.setY(curr2.getY() + 10);
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();
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