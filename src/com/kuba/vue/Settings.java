package com.kuba.vue;

import com.kuba.Game;

import java.awt.*;
import javax.swing.*;


public class Settings extends JPanel {
    JPanel background;
    JButton exit;
    JRadioButton drag, keyboard, mouse;
    ButtonGroup group = new ButtonGroup();
    Game game;

    public Settings (Game g){
        game = g;
        setSize(game.WIDTH, game.HEIGHT+35);
        background = new Background("src/resources/settings_page.png", new Dimension(game.WIDTH, game.HEIGHT));
        setLayout(null);

        // Réglage du bouton pour retourner au menu
        exit = new JButton(new ImageIcon("src/resources/return.png"));
        exit.setBounds((int)(game.WIDTH*0.92), (int)(game.HEIGHT*0.036), 50, 50);
        exit.setBorderPainted(false);
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.addActionListener(e -> {
            game.moveToMenu();
        });

        // Réglage du bouton pour le mode glissement
        drag = new JRadioButton();
        drag.setBounds((int)(game.WIDTH*0.07), (int)(game.HEIGHT*0.33), 
                       (int)(game.WIDTH*0.02), (int)(game.WIDTH*0.02));
        drag.setBackground(new Color(0, 0, 0, 0));
        drag.setSelected(true);

        // Réglage du bouton pour le mode Clavier-Souris
        keyboard = new JRadioButton();
        keyboard.setBounds((int)(game.WIDTH*0.35), (int)(game.HEIGHT*0.33), 
                           (int)(game.WIDTH*0.02), (int)(game.WIDTH*0.02));
        keyboard.setBackground(new Color(0, 0, 0, 0));

        // réglage du bouton pour le mode double clic
        mouse = new JRadioButton();
        mouse.setBounds((int)(game.WIDTH*0.72), (int)(game.HEIGHT*0.33), 
                        (int)(game.WIDTH*0.02), (int)(game.WIDTH*0.02));
        mouse.setBackground(new Color(0, 0, 0, 0));

        // Création du groupe de bontons
        group.add(keyboard);
        group.add(mouse);
        group.add(drag);

        // Ajout des composants
        background.add(keyboard);
        background.add(mouse);
        background.add(drag);
        background.add(exit);
        add(background);
    }

}