package chess;

import java.util.ArrayList;

public class MoveRook extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;

    public MoveRook(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves
        this.Piece_Moves = new ArrayList<>();

        //Store the possible positions for the bishop
        this.pos_row = position.getRow();
        this.pos_col = position.getColumn();

        //Store the gameboard for the current game.
        this.cur_game = new ChessGame();
        cur_game.setBoard(board);
    }

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        //Calculate moves for the right or towards col 8
        while(pos_col < 8){
            //Add the values
            pos_col += 1;

            // If the proposed space is not on the board, don't add it.
            if (pos_col < 1 || pos_col > 8 || pos_row < 1 || pos_col > 8){
                break;
            }

            //Attempt to add the move
            AddPiece(pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for downwards or towards row 1
        while(pos_row > 1){
            //Add the values
            pos_row -= 1;

            // If the proposed space is not on the board, don't add it.
            if (pos_col < 1 || pos_col > 8 || pos_row < 1 || pos_col > 8){
                break;
            }

            //Attempt to add the move
            AddPiece(pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for the left or towards col 1
        while(pos_col > 1){
            //Add the values
            pos_col -= 1;

            // If the proposed space is not on the board, don't add it.
            if (pos_col < 1 || pos_col > 8 || pos_row < 1 || pos_col > 8){
                break;
            }

            //Attempt to add the move
            AddPiece(pos_row,pos_col);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //Calculate moves for upwards or towards row 8
        while(pos_row < 8) {
            //Add the values
            pos_row += 1;

            // If the proposed space is not on the board, don't add it.
            if (pos_col < 1 || pos_col > 8 || pos_row < 1 || pos_col > 8){
                break;
            }

            //Attempt to add the move
            AddPiece(pos_row,pos_col);

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

        //Set pos_row and pos_col to starting position
        //pos_row = position.getRow();
        //pos_col = position.getColumn();

        //These are if the king is in check.
        ChessGame possibleGame = new ChessGame();
        ChessBoard possibleBoard = cur_game.getBoard().clone();
        ChessMove possibleMove;
        possibleGame.setBoard(possibleBoard);

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
            //Add the position to the new list of possible moves.
            Piece_Moves.add(new ChessMove(position,move,null));
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
