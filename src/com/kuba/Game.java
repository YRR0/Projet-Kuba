package com.kuba;
import java.awt.*;
import com.kuba.model.player.Joueur;
import com.kuba.vue.*;
import javax.swing.*;

public class Game extends JFrame {
    MenuView menu = new MenuView(this);
    Settings settings = new Settings(this);
    GameView board;

    public Game() {
        setSize(1000, 735);
        setContentPane(menu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kuba: The Game");
        //pack();
        //setLocationRelativeTo(null);
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
}
