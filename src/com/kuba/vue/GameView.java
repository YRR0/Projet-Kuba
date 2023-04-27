package com.kuba.vue;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.kuba.Game;
import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;

public class GameView extends JPanel {
    private final PlayerView p1, p2;
    private BoardView boardView;
    private JButton leave;
    JPanel background = new Background("src/resources/background.png", new Dimension(1000, 700));
    Game game;

    public GameView(int N, Joueur j1, Joueur j2, Game g) {
        game = g;
        setLayout(null);
        setSize(1000, 700);
        Board plateau = new Board(N);
        boardView = new BoardView(plateau, 4*N-1);
        new GameController(plateau, j1, j2);

        // Création des panneaux joueurs
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);

        // Réglage du bouton d'abandon
        leave = new JButton(new ImageIcon("src/resources/end.png"));
        leave.setBackground(new Color(0, 0, 0, 0));
        leave.setBorderPainted(false);
        leave.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.moveToEndScreen(null);
            } 
        });

        // Placement des composants
        boardView.setBounds(50,50,598,598);
        p1.setBounds(698, 173, 275, 162);
        p2.setBounds(698, 337, 275, 162);
        leave.setBounds(790, 610, 130, 60);

        // Ajout des composants
        background.add(boardView);
        background.add(p1);
        background.add(p2);
        background.add(leave);
        add(background);

        // Coloration pour indiquer le tour joueur
        p1.setBackground(new Color(255,120,0,50));
    }

    // met à jour les panneaux joueurs
    public void update(){
        p1.update();
        p2.update();
    }
    
}
