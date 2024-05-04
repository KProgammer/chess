package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        // Stores the possible positions for each piece
        int pos_row = myPosition.getRow();
        int pos_col = myPosition.getColumn();

        //Identify the type of piece (KING, QUEEN, etc.) and the find the moves it can do.
        if (piece_of_interest.type == PieceType.KING){

        }
        else if (piece_of_interest.type == PieceType.QUEEN){

        }
        else if (piece_of_interest.type == PieceType.BISHOP){
            //This loop looks for all possible moves in the upper right diagonal direction.
            while((pos_row < 8) && (pos_col < 8)){
                pos_row += 1;
                pos_col += 1;
                ChessPosition move = new ChessPosition(pos_row, pos_col);
                List_of_Moves.add(new ChessMove(myPosition,move,null));

            }

            //This code resets pos_row and pos_col to the current position of the piece
            pos_row = myPosition.getRow();
            pos_col = myPosition.getColumn();

            //This loop looks for all possible moves in the lower right diagonal direction.
            while((pos_row > 1) && (pos_col < 8)){
                pos_row -= 1;
                pos_col += 1;
                ChessPosition move = new ChessPosition(pos_row, pos_col);
                List_of_Moves.add(new ChessMove(myPosition,move,null));
            }

            //This code resets pos_row and pos_col to the current position of the piece
            pos_row = myPosition.getRow();
            pos_col = myPosition.getColumn();

            //This loop looks for all possible moves in the lower left diagonal direction.
            while((pos_row > 1) && (pos_col > 1)){
                pos_row -= 1;
                pos_col -= 1;
                ChessPosition move = new ChessPosition(pos_row, pos_col);
                List_of_Moves.add(new ChessMove(myPosition,move,null));
            }

            //This code resets pos_row and pos_col to the current position of the piece
            pos_row = myPosition.getRow();
            pos_col = myPosition.getColumn();

            //This loop looks for all possible moves in the upper left diagonal direction.
            while((pos_row < 8) && (pos_col > 1)){
                pos_row += 1;
                pos_col -= 1;
                ChessPosition move = new ChessPosition(pos_row, pos_col);
                List_of_Moves.add(new ChessMove(myPosition,move,null));
            }

        }
        else if (piece_of_interest.type == PieceType.KNIGHT){

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

