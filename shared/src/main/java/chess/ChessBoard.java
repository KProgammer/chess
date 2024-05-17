package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable{
    private ChessPiece[][] squares;
    public ChessBoard() {
        this.squares = new ChessPiece[8][8];

        //Make it so the board starts out a full set of pieces instead of starting out empty.
        //resetBoard();
    }

    /*public ChessBoard(ChessBoard chessBoard){
        ChessBoard newBoard = new ChessBoard();
        ChessBoard placeHolder = new ChessBoard();
        for(int i = 0; i < chessBoard.getBoard().length;i++){
            for(int j = 0; j < chessBoard.getBoard().length;j++){
                placeHolder.getBoard()[i][j] = chessBoard.getBoard()[i][j];
            }
        }

        newBoard.setBoard(chessBoard.squares);
    }*/

    public ChessPiece[][] getBoard(){
        return squares;
    }

    public void setBoard(ChessPiece[][] board){
        this.squares = board;
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
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Add 8 white pawns to row 2 and 8 black pawns to row 7
        for (int i = 2;i < 8; i++){
            for(int j = 1;j <= 8;j++){
                if(i<=2) {
                    addPiece(new ChessPosition(i, j), new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN));
                }
                else{
                    addPiece(new ChessPosition(i, j), new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN));
                }
            }
            i += 4;
        }

        //Add four rooks to the board
        addPiece(new ChessPosition(1,1),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1,8),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,1),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,8),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK));

        //Add four knights to the board
        addPiece(new ChessPosition(1,2),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1,7),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,2),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,7),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT));

        //Add four bishops to the board
        addPiece(new ChessPosition(1,3),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1,6),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,3),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,6),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP));

        //Add two queens to the board
        addPiece(new ChessPosition(1,4),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8,4),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN));

        //Add two kings to the board
        addPiece(new ChessPosition(1,5),new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8,5),new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING));

    }

    /**
     * Makes the object cloneable
     */
    @Override
    protected ChessBoard clone() throws CloneNotSupportedException {
        var clone = new ChessBoard();
        clone.squares = new ChessPiece[8][8];
        for (int i = 0; i < this.squares.length; i++){
            clone.squares[i] = Arrays.copyOf(this.squares[i], this.squares.length);
        }
        return clone;
    }

    /*
    public ChessBoard clone()  {
            //ChessBoard chessBoard = (ChessBoard) super.clone();
            var clone = new ChessBoard();
            ChessPiece[][] oldBoard = this.getBoard();
            ChessPiece[][] newBoard = new ChessPiece[8][8];
            for (int i = 0; i < oldBoard.length; i++) {
                newBoard[i] = Arrays.copyOf(oldBoard[i], oldBoard[i].length);
            }
            clone.setBoard(newBoard);
            return clone;
    }*/

    /**
     * Makes the object more readable when it's printed.
     */
    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }

    /**
     * Overrides how the object is compared to another object or set equal to it.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    /**
     * Affects how the object is retrieved from hashcode.
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
