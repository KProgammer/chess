package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveKing extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;
    private ChessPiece king;

    public MoveKing(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.Piece_Moves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();

        //This is to store the current game and chessboard
        cur_game = new ChessGame();
        cur_game.setBoard(board);

        //This stores the piece of interest
        this.king = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.KING);
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
        //Calculate moves for the king
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                //Reset the values of pos_row and pos_col
                pos_row = myPosition.getRow();
                pos_col = myPosition.getColumn();

                //If you are looking at the king's postion continue on
                if (pos_row == 0 && pos_col == 0){
                    continue;
                }

                //Add the values
                pos_row += i;
                pos_col += j;

                //Attempt to add the move
                AddPiece(Piece_Moves,pos_row,pos_col);
            }
        }

        return Piece_Moves;
    }
}
