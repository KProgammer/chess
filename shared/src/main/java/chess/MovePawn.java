package chess;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.abs;

public class MovePawn extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;
    private ChessPiece pawn;

    public MovePawn(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.Piece_Moves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();

        // Stores the current game board
        this.cur_game = new ChessGame();
        cur_game.setBoard(board);

        //Creates a piece similar to what we are interested in.
        this.pawn = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.PAWN);
    }

    /**
     * Calculates all moves the pawn can make
     *
     * @param myPosition The starting position of the pawn
     * @return A list of all the possible moves the king can make
     */
    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
    //If the piece is starting it may move up to two spaces if there are no pieces in the way
        if(pos_row == 2 || pos_row == 7){
        if((pos_row == 2) &&
                (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) &&
                (board.getPiece(new ChessPosition((pos_row+1),pos_col)) == null) &&
                (board.getPiece(new ChessPosition((pos_row+2),pos_col)) == null)){
            AddPiece(Piece_Moves,(pos_row+2),pos_col);
        }
        if((pos_row == 7) &&
                (pawn.getTeamColor() == ChessGame.TeamColor.BLACK) &&
                (board.getPiece(new ChessPosition((pos_row-1),pos_col)) == null) &&
                (board.getPiece(new ChessPosition((pos_row-2),pos_col)) == null)){
            AddPiece(Piece_Moves,(pos_row-2),pos_col);
        }
    }

        if(pawn.getTeamColor() == ChessGame.TeamColor.BLACK){
        pos_row -= 1;
    } else {
        pos_row += 1;
    }

    //Calculate moves for pawn
        for(int i = -1; i < 2; i++){
        //Reset the positions for pos_col
        pos_col = myPosition.getColumn();

        //Add the modifier
        pos_col += i;

        //Make sure the move is on the board. If not, skip this round.
        if (!IsOnBoard(new ChessPosition(pos_row, pos_col))){
            continue;
        }

        //If we are considering a diagonal move, there must be a piece of the opposite side to capture
        if(i != 0 && board.getPiece(new ChessPosition(pos_row,pos_col)) == null){
            //move on
            continue;
        }
        // if there is any piece in front of the pawn, it may not move
        else if (i == 0 && board.getPiece(new ChessPosition(pos_row,pos_col)) != null){
            continue;
        }

        //Otherwise attempt to add the piece.
        AddPiece(Piece_Moves,pos_row,pos_col);
    }

        return Piece_Moves;
    }
}
