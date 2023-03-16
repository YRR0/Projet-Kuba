package model.plateau;

import java.util.*;
import java.util.Timer;
import model.Bille;
import model.Couleur;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import observerpattern.Observer;
import observerpattern.SubjectObserver;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Board extends JPanel implements SubjectObserver{
    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;
    private final Set<Integer> treated_confs;
    private static int size = 50;
    private ArrayList<Observer> observers;
    private ArrayList<Joueur> joueurs;
    private int currentPlayer;
    private static int sleep_time = 5;
    private Timer timer;
    private Date dt;

    public Board(int n) {
        timer = new Timer();
        joueurs = new ArrayList<>();
        observers = new ArrayList<>();
        this.treated_confs = new HashSet<>();
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        initKeys();
        setLayout(new GridLayout(k, k));
        setPreferredSize(new Dimension(k*size, k*size));

    }

    public void addJoueur(Joueur j){
        joueurs.add(j);
        observers.add(j);
    }

    public Joueur getJoueur(){
        return this.joueurs.get(currentPlayer);
    }

    public void keyPressed(int code){
        notifyObservers(code);
    }

    private static void initKeys() {
        for(int i = 0; i < keys.length; i++) {
            for(int j = 0; j < keys[i].length; j++) {
                keys[i][j] = new Random().nextLong();
            }
        }
    }

    public void initBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(this, i, j);
                add(board(i, j));
                addObserver(board(i, j));
            }
        }
        initWhite();
        initBlack();
        initRed();
        StatAnimation();
    }

    private void initWhite() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board(i, j).setBille(new Bille(Couleur.BLANC, j, i));
                board(board.length-1 - i, board.length-1 - j).
                    setBille(new Bille(Couleur.BLANC,board.length-1 - j,board.length-1 - i));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board(i, j).setBille(new Bille(Couleur.NOIR,j,i));
                board(j, i).setBille(new Bille(Couleur.NOIR,i,j));
            }
        }
    }

    private void initRed() {
        int count = 1, spaces = 1, k = 4 * n - 1;
        for(int i = 1; i < k-1; i++) {
            if(i < k/2) {
                spaces = (k/2) - i + 1;
            } else {
                spaces = i + 1 - (k/2);
            }
            for(int j = 0; j < count; j++) {
                board(i, j+spaces).setBille(new Bille(Couleur.ROUGE,j+spaces,i));
            }
            if(i < k/2) {
                count += 2;
            } else {
                count -= 2;
            }
        }
    }

    
    private Cell board(Position pos) {
        return board[pos.getI()][pos.getJ()];
    }

    public Cell board(int i, int j) {
        return board[i][j];
    }

    public Couleur ColorAt(Position pos) {
        return board(pos).getBille().getColor();
    }

    public Couleur ColorAt(int i, int j) {
        return board(i, j).getBille().getColor();
    }

    public boolean estVide(Position position) {
        return board(position).estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board(prev).getBille();
        board(prev).clear();
        board(next).setBille(bille);
    }


    public boolean update(Position pos, Direction dir, Joueur joueur) {
        // Le joueur ne peut bouger que les billes de sa propre couleur
        if(!ColorAt(pos).equals(joueur.getCouleur())) {
            System.out.println("Le joueur ne peut bouger que les billes de sa propre couleur");
            return false;
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        if(estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
            System.out.println(pos.prev(dir));
            System.out.println("Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger");
            return false;
        }

        Position next = pos.next(dir); // Trouver la limite
        while(estDansLimite(next) && !estVide(next)) { // avec next tant que c'est possible
            next = next.next(dir);
        } 
        Position fin = next;  // Pour revenir en arriere si le mouvement n'est pas valid (un curseur backup)
        if(!estDansLimite(next)) { // là, il faut sortir les billes
            Position limit = next.prev(dir); // La limite du tableau
            moveOut(limit, joueur);
            fin = limit;
            next = next.prev(dir);
        }

        while (!next.equals(pos) && board(next).estVide()) { // Décaler les pions
            Position prev = next.prev(dir);
            updatePosition(prev, next);
            next = next.prev(dir);
        }

        int hash_code = hashCode(); // KO
        if(isTreated(hash_code)) {
            while (!next.equals(fin)) { // Undo move
                Position suiv = next.next(dir);
                updatePosition(suiv, next);
                next = next.next(dir);
            }
            return false;
        } else {
            Bille b = board((new Position(pos.getI(), pos.getJ())
                            .next(dir)))
                            .getBille();
            b.createAnimation(dir);
            treated_confs.add(hash_code);
        }
        return true;
    }

    private void moveOut(Position limit, Joueur joueur) {
        if(!ColorAt(limit).equals(joueur.getCouleur())) {
            if(ColorAt(limit).equals(Couleur.ROUGE)) {
                joueur.capturerBilleRouge();
            } else {
                joueur.capturerBilleAdversaire();
            }
            board(limit).clear();
        } else {
            System.out.println("Le joueur ne peut pas sortir les billes de sa propre couleur");
        }
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private boolean isTreated(int conf){
        return treated_confs.contains(conf);
    }

    @Override
    public String toString() {
        StringBuilder plateau = new StringBuilder();
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                plateau.append(cell.toString()).append(" ");
            }
            plateau.append("\n");
        }
        return plateau.toString();
    }

    @Override
    public int hashCode() {
        long zobristHash = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board(i, j).estVide()) {
                    switch (board[i][j].getBille().getColor()) {
                        case ROUGE : zobristHash ^= keys[0][i + board[i].length * j];
                        case NOIR : zobristHash  ^= keys[1][i + board[i].length * j];
                        case BLANC : zobristHash ^= keys[2][i + board[i].length * j];
                    }
                }
            }
        }
        return (int) zobristHash;
    }
    
    public int getN() {
    	return this.n;
    }
    public Cell[][] getCellBoard(){
    	return this.board;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }

    public void save(String path){
        try{
            FileOutputStream fileOutputStream
                = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream 
            = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Board load(String path){
        try{
            FileInputStream fileInputStream
                = new FileInputStream(path);
            ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
            Board b = (Board) objectInputStream.readObject();
            objectInputStream.close();
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

    public void animate(Graphics2D graphics2D){
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board.length;j++){
                Bille b = board(i, j).getBille();
                if (b != null){
                    graphics2D.drawImage(b.image(), b.getX()+(Bille.scale/2), 
                                                    b.getY()+(Bille.scale/2),Bille.width-Bille.scale, 
                                                    Bille.width-Bille.scale, null);
                    if (b.is_animate()){
                        b.update(
                            board(new Position(i, j).next(b.getAnimation().getDirection())).getBille()
                        );

                    }
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(i != board.length-1 && j != board[i].length-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * Bille.width + (Bille.width / 2), i * Bille.width + (Bille.width / 2), Bille.width, Bille.width);
                }
            }
        }
    }

    @Override
    public void addObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void notifyObservers(Object obj) {
        for (Observer o:observers){
            o.update(obj);
        }
    }

    public void nextPlayer(){
        currentPlayer = (1+currentPlayer)%joueurs.size();
    }

}