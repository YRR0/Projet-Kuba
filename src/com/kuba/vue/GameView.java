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
    private JButton leave, confirm, undo, redo;
    JPanel background = new Background("src/resources/background.png", new Dimension(1000, 700));
    Game game;

    public GameView(int N, Joueur j1, Joueur j2, Game g) {
        game = g;
        setLayout(null);
        setSize(1000, 700);
        Board plateau = new Board(N);
        boardView = new BoardView(plateau, 4*N-1);
        new GameController(plateau, j1, j2);
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        undo = new JButton(new ImageIcon("src/resources/undo.png"));
        undo.setBackground(new Color(0,0,0,0));
        undo.setBorderPainted(false);
        redo = new JButton(new ImageIcon("src/resources/redo.png"));
        redo.setBackground(new Color(0,0,0,0));
        redo.setBorderPainted(false);
        confirm = new JButton(new ImageIcon("src/resources/confirm.png"));
        confirm.setBackground(new Color(0,0,0,0));
        confirm.setBorderPainted(false);
        leave = new JButton(new ImageIcon("src/resources/return.png"));
        leave.setBackground(new Color(0, 0, 0, 0));
        leave.setBorderPainted(false);
        leave.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.moveToMenu();
            } 
        });

        boardView.setBounds(50,50,598,598);
        p1.setBounds(698, 173, 275, 162);
        p2.setBounds(698, 337, 275, 162);
        undo.setBounds(730, 525, 55, 55);
        redo.setBounds(890, 525, 55, 55);
        confirm.setBounds(810, 525, 55, 55);
        leave.setBounds(910, 610, 55, 55);

        background.add(boardView);
        background.add(p1);
        background.add(p2);
        background.add(leave);
        background.add(confirm);
        background.add(undo);
        background.add(redo);
        add(background);
        p1.setBackground(new Color(255,120,0,50));
    }

    public void update(){
        p1.update();
        p2.update();
    }
    
}
