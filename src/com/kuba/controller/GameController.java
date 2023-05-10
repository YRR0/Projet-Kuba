package com.kuba.controller;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.IA;
import com.kuba.vue.BilleAnimateView;
import com.kuba.vue.BoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Thread.sleep;

public class GameController {

    private final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private Direction direction;
    private final Son son;
    private final BoardView boardView;
    private Thread threadAI;

    public GameController(Board board, BoardView boardView, Joueur blanc, Joueur noir) {
        this.boardView = boardView;
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        son = new Son();
        son.playMusic(0);
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        ((JPanel)board.getObserver()).setFocusable(true);
        ((JPanel)board.getObserver()).requestFocusInWindow();
        threadAI = CreateAiThread();
    }

    private Thread CreateAiThread(){
        Thread t = new Thread() {
            public void run() {
                aiPlayer();
            }
        };
        return t;
    }

    private void deplacement(Direction d){
        try{
            if(d !=null && from != null){
                MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    lancerAnimationBille(from, d);
                    son.playSoundEffect(1);
                    changePlayer();
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    lancerAnimationBille(from, d);
                    son.playSoundEffect(1);
                } else {
                    System.out.println(moveStatus.getMessage());
                    son.playSoundEffect(2);
                }
            }
        }
        catch(Exception e){
            System.out.println("--------------------------");
        }
    }

    private void lancerAnimationBille(Position f, Direction d){
        boardView.startAnimation(f, d);
    }

    private Position positionConvert(Point a){
        for (int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                BilleAnimateView bv = boardView.getAnimatedBille(i, j);
                if (bv != null && bv.contains(a.x, a.y)) {
                    return new Position(bv.getY() / BilleAnimateView.width,
                                        bv.getX() / BilleAnimateView.width);
                }
            }
        }

        return null;
    }

    public void changePlayer(){
        if(courant == blanc){
            courant = noir;
        }else{
            courant = blanc;
        }
        if(courant instanceof IA) {
            boardView.setFocusable(false);
            threadAI = CreateAiThread();
            threadAI.start();//start the thread
        } else {
            boardView.setFocusable(true);
            boardView.requestFocusInWindow();
        }
    }

    public void pause(long milis){
        try {
            sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void aiPlayer() {
        while (boardView.isAnimating()){
            pause(1);
        }
        pause(300);
        IA aiPlayer = (IA) courant;
        Mouvement mouvement = aiPlayer.getMouvement(board);
        MoveStatus moveStatus = aiPlayer.move(board, mouvement);
        if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
            lancerAnimationBille(mouvement.getPosition(), mouvement.getDirection());
            changePlayer();
            threadAI.interrupt();
        }
        else aiPlayer();
    }

    private class KeyController extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            try{
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> {
                        direction = Direction.NORD;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_LEFT -> {
                        direction = Direction.OUEST;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_RIGHT -> {
                        direction = Direction.EST;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_DOWN -> {
                        direction = Direction.SUD;
                        deplacement(direction);
                    }
                }
            }
            catch(Exception ex){
                System.out.println("^");
            }
        }
    }

    private class MouseController extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                from = positionConvert(e.getPoint());
                //System.out.println(from);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                Position to = positionConvert(e.getPoint());
                if(from != null){
                    assert to != null;
                    direction = from.nextDir(to);
                    System.out.println(direction);
                    try{
                        if(direction != null && from != null){
                            MoveStatus moveStatus = courant.move(board, to, direction);
                            if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE) {
                                changePlayer();
                                son.playSoundEffect(1);
                                lancerAnimationBille(from, direction);
                            } else if(!moveStatus.isLegal()) {
                                System.out.println(moveStatus.getMessage());
                            }
                        }
                    }
                    catch(Exception exception){
                        System.out.print("_");
                    }
                }
            }
        }
    };
}


