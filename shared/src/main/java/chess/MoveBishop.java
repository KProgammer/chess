package chess;

import java.util.ArrayList;

public class MoveBishop extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition myPosition;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;
    private ChessPiece bishop;

    public MoveBishop(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.myPosition = position;
        //Stores the list of moves
        this.Piece_Moves = new ArrayList<ChessMove>();

        //Store the possible positions for the bishop
        this.pos_row = position.getRow();
        this.pos_col = position.getColumn();

        //Set up variable to hold the current board
        this.cur_game = new ChessGame();
        cur_game.setBoard(board);

        //This stores the current piece that we are interested in
        this.bishop = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.BISHOP);
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        //Calculate moves for the upper right diagonal or towards row 8, col 8
        while(pos_row < 8 && pos_col < 8){
            //Add the values
            pos_row += 1;
            pos_col += 1;

            // If the proposed space is not on the board, don't add it.
            if (!IsOnBoard(new ChessPosition(pos_row, pos_col))){
                break;
            }

            //Attempt to add the move
            AddPiece(Piece_Moves,pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for the lower right diagonal or towards row 1, col 8
        while(pos_row > 1 && pos_col < 8){
            //Add the values
            pos_row -= 1;
            pos_col += 1;

            // If the proposed space is not on the board, don't add it.
            if (!IsOnBoard(new ChessPosition(pos_row, pos_col))){
                break;
            }

            //Attempt to add the move
            AddPiece(Piece_Moves,pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for the lower left diagonal or towards row 1, col 1
        while(pos_row > 1 && pos_col > 1){
            //Add the values
            pos_row -= 1;
            pos_col -= 1;

            // If the proposed space is not on the board, don't add it.
            if (!IsOnBoard(new ChessPosition(pos_row, pos_col))){
                break;
            }

            //Attempt to add the move
            AddPiece(Piece_Moves,pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for the upper left diagonal or towards row 8, col 1
        while(pos_row < 8 && pos_col < 8) {
            //Add the values
            pos_row += 1;
            pos_col -= 1;

            // If the proposed space is not on the board, don't add it.
            if (!IsOnBoard(new ChessPosition(pos_row, pos_col))){
                break;
            }

            //Attempt to add the move
            AddPiece(Piece_Moves,pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }

        return Piece_Moves;
    }



    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the bishop is to be placed
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

        //If the space is empty, add it as a possible move.
        if(theBoard.getPiece(move) == null){
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(theBoard.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(),theBoard.getPiece(position));

                //See if the king is still in check.
                if (possibleGame.isInCheck(possibleBoard.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Add the position to the new list of possible moves.
            Piece_Moves.add(new ChessMove(position,move,null));
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (theBoard.getPiece(move).getTeamColor() != theBoard.getPiece(position).getTeamColor()) {
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(theBoard.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(),theBoard.getPiece(move));

                //See if the king is still in check.
                if (possibleGame.isInCheck(theBoard.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Add the position to the new list of possible moves.
            Piece_Moves.add(new ChessMove(position,move,null));
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }

    }*/
}
