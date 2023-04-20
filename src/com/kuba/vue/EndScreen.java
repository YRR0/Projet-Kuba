package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.player.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    JPanel background = new Background("src/resources/endscreen.png",new Dimension(1000, 700));
    JButton replay, exit;
    Game game;

    public EndScreen(Game g, Joueur gagnant){
        game = g;
        setSize(1000, 735);
        setLayout(null);
        JPanel popup = new JPanel();
        popup.setBounds(250, 320, 500, 300);
        popup.setBackground(new Color(0,0,0,0));
        popup.setLayout(null);
        JLabel text = new JLabel();
        JLabel re = new JLabel(" Voulez-vous rejouer ?");
        re.setBounds(150,30,500, 20);
        re.setFont(new Font("Serif", Font.BOLD, 16));
        text.setFont(new Font("Serif", Font.BOLD, 20));
        if(gagnant!=null) {
            text.setText("Bravo! "+gagnant.getNom()+" a gagné ce match!");
            text.setBounds(65, 0, 500, 20);
        } else {
            text.setText("La partie a été abandonnée.");
            text.setBounds(90, 0, 500, 20);
        }    
        background.setBackground(new Color(0,0,0,0));
        replay = new JButton(new ImageIcon("src/resources/restart.png"));
        replay.setBounds(80, 80, 130, 60);
        replay.setBackground(new Color(0,0,0,0));
        replay.setBorderPainted(false);
        replay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                game.menu.start.doClick(0);
            }
        });
        exit = new JButton(new ImageIcon("src/resources/exit.png"));
        exit.setBounds(280, 80, 130, 60);
        exit.setBackground(new Color(0,0,0,0));
        exit.setBorderPainted(false);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                game.moveToMenu();
            }
        });
        popup.add(text);
        popup.add(re);
        popup.add(replay);
        popup.add(exit);
        background.add(popup);
        add(background);
    }

}