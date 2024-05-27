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
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;
    private ChessPiece bishop;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        board = null;
        position = null;
    }

    public ChessPiece(ChessBoard board, ChessPosition position){

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.Piece_Moves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();

        //This is used to track the current game
        this.cur_game = new ChessGame();
        cur_game.setBoard(board);

        this.type = board.getPiece(position).getPieceType();
        this.pieceColor = board.getPiece(position).getTeamColor();
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
            // This line goes to the MovePawn class and calculates the possible moves
            MoveQueen Queen = new MoveQueen(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = Queen.pieceMoves(myPosition);
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
            // This line goes to the MoveKing class and calculates the possible moves
            MoveRook Rook = new MoveRook(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = Rook.pieceMoves(myPosition);
        }
        else if (piece_of_interest.type == PieceType.PAWN){
            // This line goes to the MovePawn class and calculates the possible moves
            MovePawn Pawn = new MovePawn(board,myPosition);

            // This line retrieves the calculated possible moves.
            List_of_Moves = Pawn.pieceMoves(myPosition);

        }

        return List_of_Moves;
        //throw new RuntimeException("Not implemented");
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition){
        return new ArrayList<>();
    }

    /**
     * Adds a move to the list of valid moves
     *
     * @param row The row the piece is in
     * @param col the col the piece is in
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    public void AddPiece(ArrayList<ChessMove> Piece_Moves, int row, int col) {

        //Check to see if the space exists on the board and whether it is occupied by an piece of the same team.
        if ((row < 1) || (row > 8) || (col < 1) || (col > 8) ||
                (board.getPiece(new ChessPosition(row,col)) != null &&
                        board.getPiece(new ChessPosition(row,col)).getTeamColor() == board.getPiece(position).getTeamColor())){
            return;
        } // If it is a piece that can be promoted, promote it.
        else if ((row == 1 || row == 8) && (board.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN)) {
                Piece_Moves.add(new ChessMove(position, new ChessPosition(row, col), ChessPiece.PieceType.ROOK));
                Piece_Moves.add(new ChessMove(position, new ChessPosition(row, col), ChessPiece.PieceType.KNIGHT));
                Piece_Moves.add(new ChessMove(position, new ChessPosition(row, col), ChessPiece.PieceType.BISHOP));
                Piece_Moves.add(new ChessMove(position, new ChessPosition(row, col), ChessPiece.PieceType.QUEEN));
        }
        // Otherwise just add it
        else {
            Piece_Moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }

    }

    public boolean IsOnBoard(ChessPosition possiblePos){
        // If the proposed space is not on the board, don't add it.
        if (possiblePos.getColumn() < 1 || possiblePos.getColumn() > 8 || possiblePos.getRow() < 1 || possiblePos.getRow() > 8){
            return  false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}

