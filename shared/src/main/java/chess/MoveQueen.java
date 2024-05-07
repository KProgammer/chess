package chess;

import java.util.ArrayList;

public class MoveQueen {
    private final ChessBoard theBoard;
    private final ChessPosition position;
    private ArrayList<ChessMove> Queen_Moves;
    private int pos_row;
    private int pos_col;

    public MoveQueen(ChessBoard theBoard, ChessPosition position){

        this.theBoard = theBoard;
        this.position = position;

        //Stores the list of moves
        this.Queen_Moves = new ArrayList<>();

        //Store the possible positions for the bishop
        this.pos_row = position.getRow();
        this.pos_col = position.getColumn();
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        //This loop looks for all possible moves towards the top of the board, or towards row 8.
        while(pos_row < 8){
            //Increase the position in the upper right diagonal direction
            pos_row += 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }
        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves in the upper right diagonal direction, or towards row 8 col 8.
        while((pos_row < 8) && (pos_col < 8)){
            //Increase the position in the upper right diagonal direction
            pos_row += 1;
            pos_col += 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }

        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves towards the right of the board, or column 8.
        while(pos_col < 8){
            //Increase the position in the upper right diagonal direction
            pos_col += 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }
        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves in the lower right diagonal direction.
        while((pos_row > 1) && (pos_col < 8)){
            //Increase the position in the lower right diagonal direction
            pos_row -= 1;
            pos_col += 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }
        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves towards the bottom of the board, or row 1.
        while(pos_row > 1){
            //Increase the position in the upper right diagonal direction
            pos_row -= 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }
        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves in the lower left diagonal direction.
        while((pos_row > 1) && (pos_col > 1)){
            //Increase the position in the lower left diagonal direction
            pos_row -= 1;
            pos_col -= 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }

        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves towards the left of the board, or column 1.
        while(pos_col > 1){
            //Increase the position in the upper right diagonal direction
            pos_col -= 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }
        }

        //This code resets pos_row and pos_col to the current position of the piece
        pos_row = myPosition.getRow();
        pos_col = myPosition.getColumn();

        //This loop looks for all possible moves in the upper left diagonal direction.
        while((pos_row < 8) && (pos_col > 1)){
            //Increase the position in the upper left diagonal direction
            pos_row += 1;
            pos_col -= 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //Attempt to add the move to the list of moves.
            if(!AddMove(move)){
                //If AddMove is false, the bishop should stop moving this direction
                break;
            }

        }

        return Queen_Moves;
    }

    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the bishop is to be placed
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    private boolean AddMove(ChessPosition move){
        //Create a boolean to determine if the space being considered is empty
        boolean isEmpty = false;

        //Set pos_row and pos_col to starting position
        //pos_row = position.getRow();
        //pos_col = position.getColumn();

        //If the space is empty, add it as a possible move.
        if(theBoard.getPiece(move) == null){
            //Add the position to the new list of possible moves.
            Queen_Moves.add(new ChessMove(position,move,null));
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (theBoard.getPiece(move).getTeamColor() != theBoard.getPiece(position).getTeamColor()) {
            //Add the position to the new list of possible moves.
            Queen_Moves.add(new ChessMove(position,move,null));
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }

    }
}
