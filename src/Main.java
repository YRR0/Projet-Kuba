import model.plateau.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());

        board.move(new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());
    }
}
