package chess;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class MovePawn {
    private final ChessBoard theBoard;
    private final ChessPosition position;
    private ArrayList<ChessMove> PawnMoves;
    private int pos_row;
    private int pos_col;

    public MovePawn(ChessBoard theBoard, ChessPosition position){

        this.theBoard = theBoard;
        this.position = position;

        //Stores the list of moves the king can make
        this.PawnMoves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();
    }

    /**
     * Calculates all moves the pawn can make
     *
     * @param myPosition The starting position of the pawn
     * @return A list of all the possible moves the king can make
     */
    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {
        Boolean ispieceinfront = false;
        for(int i = 1; i < 3; i++) {
            for (int j = -1; j < 2; j++) {
                //If it is a black pawn, have the rows decrement instead of increase
                if (theBoard.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK){
                    i = -i;
                }


                //If this is not the pawns first move, it may not go forward two spaces
                if (abs(i) == 2 && (position.getRow() != 2) && (position.getRow() != 7)){
                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    continue;
                }

                //Find the next column position.
                pos_row += i;
                pos_col += j;

                //check to make sure pos_col is a legitimate position. Reset and continue if not.
                if ((pos_col < 1) || (pos_col > 8) || (pos_row < 1) || (pos_row > 8)) {
                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    continue;
                }

                //If there is a piece in front of the pawn it cannot capture it and its moves forward are blocked.
                if((j == 0) && (theBoard.getPiece(new ChessPosition(pos_row, pos_col)) != null)){
                    ispieceinfront = true;

                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    continue;
                }

                //If there is a piece in front the pawn can't hop over it
                if((abs(i) == 2) && (ispieceinfront == true)){
                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    continue;
                }

                //If this is the pawn's first move, it should it can only go forward, not diagonal. Reset and continue if it tries to go diagonal
                if ((abs(i) == 2) && (j != 0)){
                    //Reset positions and continue.
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    continue;
                }



                //If the space is diagonal and is empty, the pawn cannot mover there.

                //Save the new position
                ChessPosition move = new ChessPosition(pos_row, pos_col);

                //If the move being considered is diagonal, there must be a piece there to capture.
                if((move.getColumn() != position.getColumn()) && (theBoard.getPiece(move) == null)){
                    //Set pos_row and pos_col to starting position
                    pos_row = position.getRow();
                    pos_col = position.getColumn();

                    //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                    i = abs(i);

                    //If there is no piece to capture, don't add this move.
                    continue;
                }

                //Determine if it is a legal move, add it if it is, do not if it isn't
                AddMove(move);

                //Set i back to it's original positive value if it was made negative to adapt to moving a black piece.
                i = abs(i);
            }
        }

        return PawnMoves;
    }

    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the pawn is to be placed
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    private boolean AddMove(ChessPosition move){
        //Create a boolean to determine if the space being considered is empty
        boolean isEmpty = false;

        //Set pos_row and pos_col to starting position
        pos_row = position.getRow();
        pos_col = position.getColumn();

        //If the space is empty, add it as a possible move.
        if(theBoard.getPiece(move) == null){
            //Check if it is a piece that can be promoted.
            if ((move.getRow() == 1 || move.getRow() == 8) && (theBoard.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN)){
                //Add the possible promotions that come with this move
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.ROOK));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.KNIGHT));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.BISHOP));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.QUEEN));
            }
            else {
                //Add the position to the new list of possible moves.
                PawnMoves.add(new ChessMove(position, move, null));
            }
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (theBoard.getPiece(move).getTeamColor() != theBoard.getPiece(position).getTeamColor()) {
            //Check if this piece will get promoted.
            if ((move.getRow() == 1 || move.getRow() == 8) && (theBoard.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN)){
                //Add the possible promotions that come with this move
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.ROOK));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.KNIGHT));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.BISHOP));
                PawnMoves.add(new ChessMove(position, move, ChessPiece.PieceType.QUEEN));
            }
            else {
                //Add the position to the new list of possible moves.
                PawnMoves.add(new ChessMove(position, move, null));
            }
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }

    }
}
