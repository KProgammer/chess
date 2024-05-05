package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;           //A value that holds the team
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
        //Placeholder used to figure out what moves a piece can make.
        ChessPiece piece_of_interest = board.getPiece(myPosition);

        // Stores the list of moves
        ArrayList<ChessMove> List_of_Moves = new ArrayList<>();

        //Identify the type of piece (KING, QUEEN, etc.) and the find the moves it can do.
        if (piece_of_interest.type == PieceType.KING){
            // This line goes to the MoveKing class and calculates the possible moves
            MoveKing King = new MoveKing(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = King.pieceMoves(myPosition);
        }
        else if (piece_of_interest.type == PieceType.QUEEN){

        }
        else if (piece_of_interest.type == PieceType.BISHOP){
            // This line goes to the MoveBishop class and calculates the possible moves
            MoveBishop Bishop = new MoveBishop(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = Bishop.pieceMoves(myPosition);

        }
        else if (piece_of_interest.type == PieceType.KNIGHT){
            // This line goes to the MoveKnight class and calculates the possible moves
            MoveKnight Knight = new MoveKnight(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = Knight.pieceMoves(myPosition);
        }
        else if (piece_of_interest.type == PieceType.ROOK){

        }
        else if (piece_of_interest.type == PieceType.PAWN){

        }

        return List_of_Moves;
        //throw new RuntimeException("Not implemented");
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}

