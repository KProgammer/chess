package chess;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class MovePawn extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> pieceMoves;
    private int posRow;
    private int posCol;
    private ChessGame curGame;
    private ChessPiece pawn;

    public MovePawn(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.pieceMoves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.posRow = position.getRow();
        this.posCol = position.getColumn();

        // Stores the current game board
        this.curGame = new ChessGame();
        curGame.setBoard(board);

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
        if(posRow == 2 || posRow == 7){
        if((posRow == 2) &&
                (pawn.getTeamColor() == ChessGame.TeamColor.WHITE) &&
                (board.getPiece(new ChessPosition((posRow +1), posCol)) == null) &&
                (board.getPiece(new ChessPosition((posRow +2), posCol)) == null)){
            addPiece(pieceMoves,(posRow +2), posCol);
        }
        if((posRow == 7) &&
                (pawn.getTeamColor() == ChessGame.TeamColor.BLACK) &&
                (board.getPiece(new ChessPosition((posRow -1), posCol)) == null) &&
                (board.getPiece(new ChessPosition((posRow -2), posCol)) == null)){
            addPiece(pieceMoves,(posRow -2), posCol);
        }
    }

        if(pawn.getTeamColor() == ChessGame.TeamColor.BLACK){
        posRow -= 1;
    } else {
        posRow += 1;
    }

    //Calculate moves for pawn
        for(int i = -1; i < 2; i++){
        //Reset the positions for pos_col
        posCol = myPosition.getColumn();

        //Add the modifier
        posCol += i;

        //Make sure the move is on the board. If not, skip this round.
        if (!isOnBoard(new ChessPosition(posRow, posCol))){
            continue;
        }

        //If we are considering a diagonal move, there must be a piece of the opposite side to capture
        if(i != 0 && board.getPiece(new ChessPosition(posRow, posCol)) == null){
            //move on
            continue;
        }
        // if there is any piece in front of the pawn, it may not move
        else if (i == 0 && board.getPiece(new ChessPosition(posRow, posCol)) != null){
            continue;
        }

        //Otherwise attempt to add the piece.
        addPiece(pieceMoves, posRow, posCol);
    }

        return pieceMoves;
    }
}
