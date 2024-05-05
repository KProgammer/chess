package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[(position.getRow()-1)][(position.getColumn()-1)] = piece;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[(position.getRow()-1)][(position.getColumn()-1)];
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Add white pawns to row 2 and black pawns to row 7
        for (int i = 1;i < 7; i++){
            for(int j = 0;j < 7;j++){
                if(i<=2) {
                    addPiece(new ChessPosition(i, j), new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN));
                }
                else{
                    addPiece(new ChessPosition(i, j), new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN));
                }
            }
            i += 4;
        }
        //throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
