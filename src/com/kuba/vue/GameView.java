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
    JPanel background;
    Game game;

    public GameView(int N, Joueur j1, Joueur j2, Game g) {
        game = g;
        background = new Background("src/resources/background.png", new Dimension(game.WIDTH, game.HEIGHT));
        setLayout(null);
        setSize(game.WIDTH, game.HEIGHT+35);
        Board plateau = new Board(N, game.WIDTH);
        boardView = new BoardView(plateau);
        new GameController(plateau, j1, j2);

        // Création des panneaux joueurs
        p1 = new PlayerView(j1, game.WIDTH, game.HEIGHT);
        p2 = new PlayerView(j2, game.WIDTH, game.HEIGHT);

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
        boardView.setBounds((int)(game.WIDTH*0.05),(int)(game.WIDTH*0.05),
                            (int)(game.WIDTH*0.6), (int)(game.WIDTH*0.6));
        p1.setBounds((int)(game.WIDTH*0.698), (int)(game.HEIGHT*0.247), 
                     (int)(game.WIDTH*0.275), (int)(game.HEIGHT*0.231));
        p2.setBounds((int)(game.WIDTH*0.698), (int)(game.HEIGHT*0.481), 
                     (int)(game.WIDTH*0.275), (int)(game.HEIGHT*0.231));
        leave.setBounds((int)(game.WIDTH*0.78), (int)(game.HEIGHT*0.871), 
                        (int)(game.WIDTH*0.13), (int)(game.HEIGHT*0.086));

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
