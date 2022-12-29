//créé un vaisseau rouge déplacable avec les flèches du clavier

//charge la classe principale com.apogee.dev.DuoVaders.MainActivity
//qui contient la méthode main

package com.apogee.dev.DuoVaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;


//importe le module de géstion des images
import javafx.scene.image.Image;

public class helloFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        //créé un rectangle de 50x50 pixels
        Rectangle r = new Rectangle(50, 50);
        //créé un deuxième rectangle de 50x50 pixels
        Rectangle r2 = new Rectangle(50, 50);
        //les rempli de noir
        r.setFill(Color.BLACK);
        r2.setFill(Color.BLACK);
        //créé un panneau
        Pane p = new Pane();
        //ajoute les rectangles au panneau
        p.getChildren().add(r);
        p.getChildren().add(r2);
        //créé une scène de 500x500 pixels
        Scene s = new Scene(p, 500, 500);
        //place r au centre de la scène en bas
        r.setX(s.getWidth() / 2 - r.getWidth() / 2);
        r.setY(s.getHeight() - r.getHeight());
        //place r2 au centre de la scène en haut
        r2.setX(s.getWidth() / 2 - r2.getWidth() / 2);
        r2.setY(0);
        //projectiles
        Rectangle b = new Rectangle(10, 10);
        Rectangle b2 = new Rectangle(10, 10);
        //les remplis de rouge
        b.setFill(Color.RED);
        b2.setFill(Color.RED);
        //place b au centre du rectangle r
        b.setX(r.getX() + r.getWidth() / 2 - b.getWidth() / 2);
        b.setY(r.getY() - b.getHeight());
        //place b2 au centre du rectangle r2
        b2.setX(r2.getX() + r2.getWidth() / 2 - b2.getWidth() / 2);
        b2.setY(r2.getY() + r2.getHeight());
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
                p.getChildren().add(b);
                b.setX(r.getX() + r.getWidth() / 2 - b.getWidth() / 2);
                //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
                new Thread(() -> {
                    while (b.getY() > -10) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        b.setY(b.getY() - 10);
                    }
                }).start();
            }
             // si la touche est la touche e, ajoute un projectile au niveau du vaisseau r2
            if (e.getCode() == KeyCode.E) {
                //ajoute le rectangle b au panneau
                p.getChildren().add(b2);
                b2.setX(r2.getX() + r2.getWidth() / 2 - b2.getWidth() / 2);
                b2.setY(r2.getY() - b2.getHeight());
                //déplace le rectangle de 10 pixels vers le haut automatiquement toutes les 0.5s
                new Thread(() -> {
                    while (b2.getY() < s.getHeight()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        b2.setY(b2.getY() + 10);
                    }
                }).start();
            }
        });
        //ajoute la scène au stage
        primaryStage.setScene(s);
        //affiche le stage
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}