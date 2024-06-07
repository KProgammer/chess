import chess.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        //DisplayBoard board = new DisplayBoard();
        //board.main(args);
        DisplayBoard.main(args, ChessGame.TeamColor.WHITE, new ChessGame());
    }
}