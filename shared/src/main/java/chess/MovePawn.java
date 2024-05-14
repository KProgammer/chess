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
        if ((pos_row < 1) || (pos_row > 8) || (pos_col < 1) || (pos_col > 8)){
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

    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the pawn is to be placed
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    /*private boolean AddMove(ChessPosition move){
        //Create a boolean to determine if the space being considered is empty
        boolean isEmpty = false;

        //These are if the king is in check.
        ChessGame possibleGame = new ChessGame();
        ChessBoard possibleBoard = cur_game.getBoard().clone();
        ChessMove possibleMove;
        possibleGame.setBoard(possibleBoard);

        //Set pos_row and pos_col to starting position
        pos_row = position.getRow();
        pos_col = position.getColumn();

        //If the space is empty, add it as a possible move.
        if(board.getPiece(move) == null){
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(board.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), board.getPiece(move));

                //See if the king is still in check.
                if (possibleGame.isInCheckmate(board.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Check if it is a piece that can be promoted.
            if ((move.getRow() == 1 || move.getRow() == 8) && (board.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN)){
                //Add the possible promotions that come with this move
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.ROOK));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.KNIGHT));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.BISHOP));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.QUEEN));
            }
            else {
                //Add the position to the new list of possible moves.
                Piece_Moves.add(new ChessMove(position, move, null));
            }
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (board.getPiece(move).getTeamColor() != board.getPiece(position).getTeamColor()) {
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(board.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), board.getPiece(move));

                //See if the king is still in check.
                if (possibleGame.isInCheckmate(board.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Check if this piece will get promoted.
            if ((move.getRow() == 1 || move.getRow() == 8) && (board.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN)){
                //Add the possible promotions that come with this move
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.ROOK));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.KNIGHT));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.BISHOP));
                Piece_Moves.add(new ChessMove(position, move, ChessPiece.PieceType.QUEEN));
            }
            else {
                //Add the position to the new list of possible moves.
                Piece_Moves.add(new ChessMove(position, move, null));
            }
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }

    }*/
}
