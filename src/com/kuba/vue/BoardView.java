package com.kuba.vue;


import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;                                                            
import java.util.Date;    

public class BoardView extends JPanel implements Observer<Data> {

    private Data board;
    public static int HEIGHT;
    private final Timer timer;
    private static final int sleep_time = 5;
    private Date dt;
    private BufferedImage layout;

    public BoardView(Board board){
        try { layout = ImageIO.read(new File("src/resources/layout.png")); }
        catch (IOException io) { System.out.println("how?"); }
        setBackground(new Color(0,0,0,0));
        timer = new Timer();
        this.board = board;
        board.addObserver(this);
        StatAnimation();
    }

    private void drawGrid(Graphics2D graphics2D) {
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                graphics2D.drawImage(layout, i*Bille.width, j*Bille.width, Bille.width, Bille.width, null);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        animate(graphics2D);
    }

    public void StatAnimation(){
        dt = new Date (System.currentTimeMillis () + sleep_time);
        timer.schedule(update(), dt);

    }

    private TimerTask update(){
        return new TimerTask() {
            @Override
            public void run() {
                MouseInfo.getPointerInfo ();
                repaint();
                dt = new Date (dt.getTime () + sleep_time);
		        timer.schedule(update (), dt);
            }
        };   
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.size() && j >= 0 && j < board.size();
    }

    public void animate(Graphics2D graphics2D){
        for (int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                Bille b = board.board(i, j).getBille();
                graphics2D.drawImage(layout, i*Bille.width, j*Bille.width, Bille.width, Bille.width, null);
                if (b != null){
                    graphics2D.drawImage(b.image(), b.getX(),
                                                    b.getY(),Bille.width,
                                                    Bille.width, null);

                    if (b.is_animate()){
                        Position neibPos = new Position(i, j).next(b.getAnimation().getDirection());
                        b.update(
                            estDansLimite(neibPos) ? board.board(neibPos.getI(), neibPos.getJ()).getBille():null
                        );

                    }
                }
            }
        }
    }



    @Override
    public void update(Data e){
        board = e;
        this.repaint();
    }

}
