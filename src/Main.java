import model.mouvement.*;
import model.plateau.Board;
import model.plateau.Joueur;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());

        board.move(new Joueur("emma", 2, false),
                new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());
    }
}
