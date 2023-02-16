import javax.swing.JFrame;

import model.mouvement.Direction;
import model.mouvement.Position;
import model.plateau.Board;
import view.MenuView;


public class Main {
    private static MenuView view;

    public static void main(String[] args) {
        Board board = new Board(3);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());
        
        view = new MenuView();
        view.setVisible(true);
       
        board.move(new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.move(new Position(1, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.move(new Position(3, 2), Direction.NORD);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());
        
    }
}