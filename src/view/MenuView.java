package view;

import model.plateau.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class MenuView extends JFrame {
    static MenuView accessor;
    BufferedImage background;
    JComboBox<Integer> boardSizes;
    JTextField playerOne = new JTextField("Joueur 1");
    JTextField playerTwo = new JTextField("Joueur 2");
    JButton start;
    
    public MenuView(){
        this.setSize(1200,900);
        this.setTitle("Kuba");
        accessor = this;
        JPanel menu = new JPanel(null);

        Integer[] choices = {1, 2, 3, 4, 5};
        boardSizes = new JComboBox<Integer>(choices);
        boardSizes.setSelectedIndex(2);

        start = new JButton("Lancer");
        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board board = new Board(boardSizes.getSelectedIndex()+1);
                BoardView b = new BoardView(board);
                board.addObserver(b);
                JFrame frame = MenuView.accessor;
                frame.setContentPane(b);   
                frame.invalidate();
                frame.validate();    
            }
        });

        playerOne.setBounds(180,200,200,50);
        playerTwo.setBounds(820,200,200,50);
        start.setBounds(500,325,200,100);
        boardSizes.setBounds(560,250,80,50);

        menu.add(playerOne);
        menu.add(boardSizes);
        menu.add(playerTwo);
        menu.add(start);

        this.setContentPane(menu);
    }
}
