package chess;

import java.util.ArrayList;

public class MoveKnight {
    private final ChessBoard theBoard;
    private final ChessPosition position;
    private ArrayList<ChessMove> KnightMoves;
    private int pos_row;
    private int pos_col;

    public MoveKnight(ChessBoard theBoard, ChessPosition position){

        this.theBoard = theBoard;
        this.position = position;

        //Stores the list of moves the king can make
        this.KnightMoves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();
    }

    /**
     * Calculates all moves the knight can make
     *
     * @param myPosition The starting position of the knight
     * @return A list of all the possible moves the knight can make
     */
    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
        //These two for loops traverse from left to right through the squares around the knight starting
        //on the direction facing the first row. They use addition and subtraction to get to the squares
        //shown below.
        /*
                        | | | | | | | | |
                        | | | |x| |x| | |
                        | | |x| | | |x| |
                        | | | | |N| | | |
                        | | |x| | | |x| |
                        | | | |x| |x| | |
                        | | | | | | | | |
                        | | | | | | | | |
        */
        for(int i = -2; i <= 2; i++) {
            for (int j = -2; j < 3; j += 2) {

                //The if statements below allow the program to follow the pattern shown in the schematic above.
                //If i is zero have it increase itself by 1
                if (i == 0) {
                    i += 1;
                }
                //If i is even and j is -2
                else if(i%2 == 0 && j == -2){
                    j = -1;
                }
                //If i is odd and j is 0
                else if(i%2 != 0 && j == 0){
                    j = 2;
                }
                pos_row += i;
                pos_col += j;

                //check to make sure pos_row and pos_column are legitimate positions. Reset and continue if not.
                if((pos_col < 1) || (pos_col > 8) || (pos_row < 1) || (pos_row > 8)){
                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();
                    continue;
                }

                //Save the new position
                ChessPosition move = new ChessPosition(pos_row, pos_col);

                //Determine if it is a legal move, add it if it is, do not if it isn't
                AddMove(move);
            }
        }

        return KnightMoves;
    }

    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the knight is to be placed
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    private boolean AddMove(ChessPosition move) {
        //Create a boolean to determine if the space being considered is empty
        boolean isEmpty = false;

        //Set pos_row and pos_col to starting position
        pos_row = position.getRow();
        pos_col = position.getColumn();

        //If the space is empty, add it as a possible move.
        if (theBoard.getPiece(move) == null) {
            //Add the position to the new list of possible moves.
            KnightMoves.add(new ChessMove(position, move, null));
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (theBoard.getPiece(move).getTeamColor() != theBoard.getPiece(position).getTeamColor()) {
            //Add the position to the new list of possible moves.
            KnightMoves.add(new ChessMove(position, move, null));
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }
    }
}