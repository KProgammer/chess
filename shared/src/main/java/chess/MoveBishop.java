package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveBishop {
    private final ChessBoard theBoard;
    private final ChessPosition position;
    //Collection<ChessMove>

    public MoveBishop(ChessBoard theBoard, ChessPosition position){

        this.theBoard = theBoard;
        this.position = position;
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
        // Stores the list of moves
        ArrayList<ChessMove> Bishop_Moves = new ArrayList<>();

        // Stores the possible positions for each piece
        int pos_row = myPosition.getRow();
        int pos_col = myPosition.getColumn();

        //This loop looks for all possible moves in the upper right diagonal direction.
        while((pos_row < 8) && (pos_col < 8)){
            //Increase the position in the upper right diagonal direction
            pos_row += 1;
            pos_col += 1;

            //Save the new position
            ChessPosition move = new ChessPosition(pos_row, pos_col);

            //If the space is empty, add it as a possible move.
            if(theBoard.getPiece(move) == null){
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
            }
            //If the space contains a piece on the same team, end all possible moves in this direction.
            else if (theBoard.getPiece(move).getTeamColor() == theBoard.getPiece(myPosition).getTeamColor()){
                break;
            }
            //If the space contains an opposing piece, add the space as a possible move and then end all
            //possible moves in this direction.
            else {
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
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

            //If the space is empty, add it as a possible move.
            if(theBoard.getPiece(move) == null){
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
            }
            //If the space contains a piece on the same team, end all possible moves in this direction.
            else if (theBoard.getPiece(move).getTeamColor() == theBoard.getPiece(myPosition).getTeamColor()){
                break;
            }
            //If the space contains an opposing piece, add the space as a possible move and then end all
            //possible moves in this direction.
            else {
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
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

            //If the space is empty, add it as a possible move.
            if(theBoard.getPiece(move) == null){
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
            }
            //If the space contains a piece on the same team, end all possible moves in this direction.
            else if (theBoard.getPiece(move).getTeamColor() == theBoard.getPiece(myPosition).getTeamColor()){
                break;
            }
            //If the space contains an opposing piece, add the space as a possible move and then end all
            //possible moves in this direction.
            else {
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
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

            //If the space is empty, add it as a possible move.
            if(theBoard.getPiece(move) == null){
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
            }
            //If the space contains a piece on the same team, end all possible moves in this direction.
            else if (theBoard.getPiece(move).getTeamColor() == theBoard.getPiece(myPosition).getTeamColor()){
                break;
            }
            //If the space contains an opposing piece, add the space as a possible move and then end all
            //possible moves in this direction.
            else {
                //Add the position to the new list of possible moves.
                Bishop_Moves.add(new ChessMove(myPosition,move,null));
                break;
            }
        }

        return Bishop_Moves;
    }
}
