package com.kuba.vue;


import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class BoardView extends JPanel implements Observer<Data> {

    private Data board;
    public static int HEIGHT = 598;
    private final Timer timer;
    private static final int sleep_time = 5;
    private Date dt;
    private int size;

    public BoardView(Board board) {
        size = board.size();
        setBackground(new Color(0,0,0,0));
        timer = new Timer();
        this.board = board;
        board.addObserver(this);
        setPreferredSize(new Dimension(598, 598));
        setSize(new Dimension(600, 600));
        StatAnimation();
    }
    private void drawGrid(Graphics2D graphics2D) {
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(i != board.size()-1 && j != board.size()-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.setStroke(new BasicStroke(100/size));
                    int width = 598/size;
                    graphics2D.drawRect(j * width + (width / 2), i * width + (width / 2), width, width);
                }
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
