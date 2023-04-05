package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MenuView extends JPanel {
    JPanel background = new Background("src/resources/main_title.png", new Dimension(1000, 700));
    String[] choices = {" 3x3", " 7x7", "11x11", "15x15", "19x19"};
    JTextField playerOne, playerTwo;
    JCheckBox botOne, botTwo;
    JComboBox<String> boardSizes;
    JButton start, settings;
    Game game;
    public MenuView(Game g) {
        game = g;
        //setPreferredSize(screenSize);
        setSize(1000,735);
        setLayout(null);

        initPlayers();
        initBots();
        initButtons();

        background.add(playerOne);
        background.add(playerTwo);
        background.add(botOne);
        background.add(botTwo);
        background.add(boardSizes);
        background.add(start);
        background.add(settings);
        add(background);
        new MenuController();
    }

    private void initPlayers() {
        playerOne = new JTextField("Joueur 1");
        playerOne.addFocusListener(new FocusListener() {            
            @Override
            public void focusGained(FocusEvent e){
                playerOne.setOpaque(false);
                if (playerOne.getText().equals("Joueur 1")) playerOne.setText("");
            }

            @Override
            public void focusLost(FocusEvent e){
                playerOne.setOpaque(true);
                if (playerOne.getText().equals("")) playerOne.setText("Joueur 1");
            }
        });
        playerTwo = new JTextField("Joueur 2");
        playerTwo.addFocusListener(new FocusListener() {            
            @Override
            public void focusGained(FocusEvent e){
                playerTwo.setOpaque(false);
                if (playerTwo.getText().equals("Joueur 2")) playerTwo.setText("");
            }

            @Override
            public void focusLost(FocusEvent e){
                playerTwo.setOpaque(true);
                if (playerTwo.getText().equals("")) playerTwo.setText("Joueur 2");
            }
        });
        stylePlayers();
    }

    private void stylePlayers() {
        playerOne.setFont(new Font("Arial", Font.BOLD, 18));
        playerOne.setBackground(new Color(0,0,0,40));
        playerOne.setBounds(228, 340, 175, 38);
        playerOne.setBorder(BorderFactory.createEmptyBorder());
        playerTwo.setFont(new Font("Arial", Font.BOLD, 18));
        playerTwo.setBackground(new Color(0,0,0,40));
        playerTwo.setBounds(661, 340, 175, 38);
        playerTwo.setBorder(BorderFactory.createEmptyBorder());
    }

    private void initBots() {
        botOne = new JCheckBox();
        botTwo = new JCheckBox();
        styleBots();
    }

    private void styleBots() {
        botOne.setBackground(new Color(0,0,0,0));
        botOne.setBounds(245, 402, 19, 19);
        botOne.setBorderPainted(false);
        botTwo.setBackground(new Color(0,0,0,0));
        botTwo.setBounds(677, 402, 19, 19);
        botTwo.setBorderPainted(false);
    }

    private void initButtons() {
        boardSizes = new JComboBox<String>(choices);
        start = new JButton(new ImageIcon("src/resources/launch.png"));
        settings = new JButton(new ImageIcon("src/resources/settings.png"));
        boardSizes.setSelectedIndex(1);
        styleButtons();
    }

    private void styleButtons() {
        boardSizes.setFont(new Font("Arial", Font.BOLD, 9));
        boardSizes.setBounds(573, 458, 55, 45);
        start.setBounds(470, 570, 130, 60);
        start.setBackground(new Color(0, 0, 0, 0));
        start.setBorderPainted(false);
        settings.setBounds(920, 25, 50, 50);
        settings.setBorderPainted(false);
        settings.setBackground(new Color(0, 0, 0, 0));
    }

    private class MenuController extends MouseAdapter {

        public MenuController() {
            start.addActionListener(e -> {
                int i = boardSizes.getSelectedIndex() + 1;
                Joueur j1 = new Joueur(playerOne.getText(), Couleur.BLANC);
                Joueur j2 = new Joueur(playerTwo.getText(), Couleur.NOIR);
                game.moveToBoard(i,j1, j2);
            });

            settings.addActionListener(e -> {
                game.moveToSettings();
            });
        }
    }
}
