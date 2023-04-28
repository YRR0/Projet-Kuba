package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.player.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    JPanel background;
    JButton replay, exit;
    Game game;

    public EndScreen(Game g, Joueur gagnant){
        game = g;
        background = new Background("src/resources/endscreen.png",new Dimension(game.WIDTH, game.HEIGHT));
        setSize(game.WIDTH, game.HEIGHT+35);
        setLayout(null);
        // Paramètrage des textes
        JLabel text = new JLabel();
        JLabel re = new JLabel(" Voulez-vous rejouer ?");
        re.setBounds((int)(game.WIDTH*0.39), (int)(game.HEIGHT*0.5), (int)(game.WIDTH*0.5), (int)(game.HEIGHT*0.029));
        re.setFont(new Font("Serif", Font.BOLD, 16));
        text.setFont(new Font("Serif", Font.BOLD, (int)(game.WIDTH*0.016)+(int)(game.HEIGHT*0.008)));
        if(gagnant!=null) {
            text.setText("Bravo! "+gagnant.getNom()+" a gagné ce match!");
            text.setBounds((int)(game.WIDTH*0.315), (int)(game.HEIGHT*0.457), 
                           (int)(game.WIDTH*0.5), (int)(game.HEIGHT*0.029));
        } else {
            text.setText("La partie a été abandonnée.");
            text.setBounds((int)(game.WIDTH*0.34),(int)(game.HEIGHT*0.457), 
                           (int)(game.WIDTH*0.5), (int)(game.HEIGHT*0.029));
        }

        // Paramètrage du bouton de relance
        replay = new JButton(new ImageIcon("src/resources/restart.png"));
        replay.setBounds((int)(game.WIDTH*0.33), (int)(game.HEIGHT*0.571), 
                         (int)(game.WIDTH*0.13), (int)(game.HEIGHT*0.086));
        replay.setBackground(new Color(0,0,0,0));
        replay.setBorderPainted(false);
        replay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                game.menu.start.doClick(0);
            }
        });

        // Paramètrage du bouton de retour au menu
        exit = new JButton(new ImageIcon("src/resources/exit.png"));
        exit.setBounds((int)(game.WIDTH*0.53), (int)(game.HEIGHT*0.571), 
                       (int)(game.WIDTH*0.13), (int)(game.HEIGHT*0.086));
        exit.setBackground(new Color(0,0,0,0));
        exit.setBorderPainted(false);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                game.moveToMenu();
            }
        });

        // Ajout des composants
        background.add(text);
        background.add(re);
        background.add(replay);
        background.add(exit);
        add(background);
    }

}