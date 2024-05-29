package chess;

import java.util.ArrayList;

public class MoveKing extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> pieceMoves;
    private int posRow;
    private int posCol;
    private ChessGame curGame;
    private ChessPiece king;

    public MoveKing(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.pieceMoves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.posRow = position.getRow();
        this.posCol = position.getColumn();

        //This is to store the current game and chessboard
        curGame = new ChessGame();
        curGame.setBoard(board);

        //This stores the piece of interest
        this.king = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.KING);
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
        //Calculate moves for the king
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                //Reset the values of pos_row and pos_col
                posRow = myPosition.getRow();
                posCol = myPosition.getColumn();

                //If you are looking at the king's postion continue on
                if (posRow == 0 && posCol == 0){
                    continue;
                }

                //Add the values
                posRow += i;
                posCol += j;

                //Attempt to add the move
                addPiece(pieceMoves, posRow, posCol);
            }
        }

        return pieceMoves;
    }
}
