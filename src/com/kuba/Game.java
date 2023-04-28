package com.kuba;

import com.kuba.model.player.Joueur;
import com.kuba.vue.*;

import java.awt.Toolkit;

import javax.swing.*;

public class Game extends JFrame {
    public final int WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*7.3)/10;
    public final int HEIGHT = (int)(WIDTH * 0.7);

    public MenuView menu = new MenuView(this);
    private Settings settings = new Settings(this);
    private GameView board;

    public Game() {
        setSize(WIDTH, HEIGHT+35);
        setContentPane(menu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kuba: The Game");
        setVisible(true);
    }

    public void moveToSettings(){
        setContentPane(settings);
        invalidate();
        validate();
    }

    public void moveToMenu(){
        setContentPane(menu);
        invalidate();
        validate();
    }

    public void moveToBoard(int n, Joueur j1, Joueur j2){     
        board = new GameView(n, j1, j2, this);
        setContentPane(board);
        invalidate();
        validate();
    }

    public void moveToEndScreen(Joueur gagnant){
        setContentPane(new EndScreen(this, gagnant));
        invalidate();
        validate();
    }
}
