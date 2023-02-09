package plateau;

import mouvement.Direction;
import mouvement.Position;
import java.util.Random;


public class Board {

    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;


    public Board(int n) {
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        initKeys();
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
                board[i][j] = new Cell();
            }
        }
        initWhite();
        initBlack();
        initRed();
    }

    private void initWhite() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j].setBille(new Bille(Couleur.BLANC, new Position(i, j)));
                board[board.length-1 - i][board.length-1 - j].setBille(new Bille(Couleur.BLANC, new Position(i, j)));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board[i][j].setBille(new Bille(Couleur.NOIR, new Position(i, j)));
                board[j][i].setBille(new Bille(Couleur.NOIR, new Position(i, j)));
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
                board[i][j+spaces].setBille(new Bille(Couleur.ROUGE, new Position(i, j)));
            }
            if(i < k/2) {
                count += 2;
            } else {
                count -= 2;
            }
        }
    }

    public void bouger(Position pos, Direction dir) {
        int x = pos.gPosI(), y = pos.gPosJ();
        int dx = dir.gDirI(), dy = dir.gDirJ();

        while(estDansLimite(x+dx, y+dy) && !board[x][y].estVide()) {
            x += dx;
            y += dy;
        }

        if(dx == 0) {
            for(int i = y; i != pos.gPosJ(); i -= dy) {
                board[x][i].setBille(board[x][i-dy].getBille());
                board[x][i-dy].clear();
            }
        }
        if(dy == 0) {
            for(int i = x; i != pos.gPosI(); i -= dx) {
                board[i][y].setBille(board[i-dx][y].getBille());
                board[i-dx][y].clear();
            }
        }

    }

    private boolean estDansLimite(int i, int j) {
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
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
                if(!board[i][j].estVide()) {
                    switch (board[i][j].getBille().getColor()) {
                        case ROUGE -> zobristHash = zobristHash ^ keys[0][i + board[i].length * j];
                        case NOIR -> zobristHash = zobristHash ^ keys[1][i + board[i].length * j];
                        case BLANC -> zobristHash = zobristHash ^ keys[2][i + board[i].length * j];
                    }

                }
            }
        }
        return (int) zobristHash;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }

    public Cell[][] getBoard() {
        return board;
    }
}
