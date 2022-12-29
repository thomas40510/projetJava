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


//importe le module de gestion des images
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class helloFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        //valeur vie
        int vie1 = 20;
        // créé des carrés de 10*10 pixels de couleur rouge (enemy)
        Rectangle en1 = new Rectangle(30, 30, Color.RED);
        Rectangle en2 = new Rectangle(30, 30, Color.RED);
        Rectangle en3 = new Rectangle(30, 30, Color.RED);
        Image alien = new Image("https://static.vecteezy.com/ti/vecteur-libre/p3/3134697-dessin-illustration-du-vaisseau-spatial-gratuit-vectoriel.jpg");
        en1.setFill(new ImagePattern( alien , 0, 0, 1, 1, true));
        en2.setFill(new ImagePattern( alien , 0, 0, 1, 1, true));
        en3.setFill(new ImagePattern( alien , 0, 0, 1, 1, true));
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
        p.getChildren().add(en1);
        p.getChildren().add(en2);
        p.getChildren().add(en3);
        //ajoute la vie au panneau (texte)
        p.getChildren().add(new javafx.scene.text.Text(10, 20, " Vie : " + vie1));
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
        //place les ennemis au centre de la scène
        en1.setX(s.getWidth() / 2 - en1.getWidth() / 2 - 60);
        en1.setY(s.getHeight() / 2 - en1.getHeight() / 2);
        en2.setX(s.getWidth() / 2 - en2.getWidth() / 2);
        en2.setY(s.getHeight() / 2 - en2.getHeight() / 2);
        en3.setX(s.getWidth() / 2 - en3.getWidth() / 2 + 60);
        en3.setY(s.getHeight() / 2 - en3.getHeight() / 2);
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