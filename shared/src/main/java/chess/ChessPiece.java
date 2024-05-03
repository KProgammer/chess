package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessPiece piece_of_interest;               //Placeholder used to figure out what moves a piece can make.
    private final ChessGame.TeamColor pieceColor;           //A value that holds the team
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //Get the piece that we are interested in moving
        piece_of_interest = board.getPiece(myPosition);

        // Stores the list of moves
        ArrayList<Integer> List_of_Moves = new ArrayList<Integer>();

        // Stores the possible positions for each piece
        int pos_row;
        int pos_col;

        //Identify the type of piece (KING, QUEEN, etc.) and the find the moves it can do.
        if (piece_of_interest.type == PieceType.KING){

        }
        else if (piece_of_interest.type == PieceType.QUEEN){

        }
        else if (piece_of_interest.type == PieceType.BISHOP){
            //Working on implementing this code
            //while((pos_row < 9) && (pos_col<9)){
            //    List_of_Moves.add([pos_row,pos_col]);
           // }
        }
        else if (piece_of_interest.type == PieceType.KNIGHT){

        }
        else if (piece_of_interest.type == PieceType.ROOK){

        }
        else if (piece_of_interest.type == PieceType.PAWN){

        }

        return new ArrayList<>();
        //throw new RuntimeException("Not implemented");
    }
}
