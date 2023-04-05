package com.kuba.vue;

import com.kuba.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*;


public class Settings extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel background = new Background("src/resources/settings_page.png", new Dimension(1000, 700));
    JButton exit;
    JRadioButton drag, keyboard, mouse;
    ButtonGroup group = new ButtonGroup();
    Game game;

    public Settings (Game g){
        setSize(1000, 735);
        setLayout(null);
        game = g;
        exit = new JButton(new ImageIcon("src/resources/return.png"));
        exit.setBounds(920, 25, 50, 50);
        exit.setBorderPainted(false);
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.addActionListener(e -> {
            game.moveToMenu();
        });
        drag = new JRadioButton();
        drag.setBounds(70, 230, 20, 20);
        drag.setBackground(new Color(0, 0, 0, 0));
        drag.setSelected(true);
        keyboard = new JRadioButton();
        keyboard.setBounds(350, 230, 20, 20);
        keyboard.setBackground(new Color(0, 0, 0, 0));
        mouse = new JRadioButton();
        mouse.setBounds(720, 230, 20, 20);
        mouse.setBackground(new Color(0, 0, 0, 0));
        group.add(keyboard);
        group.add(mouse);
        group.add(drag);
        background.add(keyboard);
        background.add(mouse);
        background.add(drag);
        background.add(exit);
        add(background);
    }

}