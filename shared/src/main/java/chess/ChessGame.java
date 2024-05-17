package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard cur_board;
    private TeamColor cur_turn;

    public ChessGame() {
        this.cur_board = new ChessBoard();
        cur_board.resetBoard();
        this.cur_turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return cur_turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        cur_turn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(cur_board, chessGame.cur_board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cur_board);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (cur_board.getPiece(startPosition) == null){
            return null;
        } // Check if the king is in check.

        //These lines are used to store a possible chessboard
        ChessGame possibleGame = new ChessGame();
        ChessBoard possibleBoard;
        try {
            possibleBoard = cur_board.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        //This line stores the moves of the chess piece
        Collection<ChessMove> movesOfInterest = new ChessPiece(cur_board.getPiece(startPosition).getTeamColor(),
                cur_board.getPiece(startPosition).getPieceType()).pieceMoves(cur_board,startPosition);

        //this collects all the valid moves for the current piece
        Collection<ChessMove> Val_Moves = new ArrayList<>();

        //Set the possible game to have the same chessboard as the current game.
        possibleGame.setBoard(possibleBoard);

        //This stores the move that is being considered
        ChessMove possibleMove;


        if (isInCheck(cur_board.getPiece(startPosition).getTeamColor())) {
            //If the king is in check we must check if any of the moves will bring the king out of check
            for (ChessMove chessMove : movesOfInterest) {
                //reset the board to the current board
                try {
                    possibleBoard = cur_board.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }

                //Add the move possible move
                possibleMove = chessMove;

                //Clear both squares
                possibleBoard.addPiece(startPosition, null);
                possibleBoard.addPiece(possibleMove.getEndPosition(), null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), cur_board.getPiece(startPosition));

                //Set the new board to the possible game.
                possibleGame.setBoard(possibleBoard);

                //If the king is not in check, add the move to the Val_Moves variable
                if (!possibleGame.isInCheck(possibleBoard.getPiece(possibleMove.getEndPosition()).getTeamColor())) {
                    Val_Moves.add(possibleMove);
                }

            }
        }
        else{
            //If the king is not check we must check if any of the moves will bring the king into check
            for (ChessMove chessMove : movesOfInterest) {
                //reset the board to the current board
                try {
                    possibleBoard = cur_board.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }

                //Add the move possible move
                possibleMove = chessMove;

                //Clear both squares
                possibleBoard.addPiece(startPosition, null);
                possibleBoard.addPiece(possibleMove.getEndPosition(), null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), cur_board.getPiece(startPosition));

                //Set the new board to the possible game.
                possibleGame.setBoard(possibleBoard);


                //If the king is not in check, add the move to the Val_Moves variable
                if (!possibleGame.isInCheck(possibleBoard.getPiece(possibleMove.getEndPosition()).getTeamColor())) {
                    Val_Moves.add(possibleMove);
                }

            }
        }
        return Val_Moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
         //This is a boolean to store if the move is a valid ChessMove
         boolean isAMove = false;

         //This is to point towards the chess piece were are looking at.
         ChessPiece pieceOfInterest = null;

         if (cur_board.getPiece(move.getStartPosition()) != null) {
             //Make a placeholder to point to the chesspiece we are interested
             pieceOfInterest = cur_board.getPiece(move.getStartPosition());

             //Look through all the possible moves of the piece and see if move is one of them
             for (ChessMove chessMove : validMoves(move.getStartPosition())) {
                 if (chessMove.equals(move)) {
                     isAMove = true;
                     break;
                 }
             }

             //Check if the piece is the same color as the team whose turn it is.
             if (pieceOfInterest.getTeamColor() != cur_turn) {
                 isAMove = false;
             }
         }

         //If it's true then make the move
         if (isAMove) {
             if(move.getPromotionPiece() != null){
                 //Add the piece to its new position.
                 cur_board.addPiece(move.getEndPosition(), new ChessPiece(pieceOfInterest.getTeamColor(),move.getPromotionPiece()));
             }else {
                 //Add the piece to its new position.
                 cur_board.addPiece(move.getEndPosition(), cur_board.getPiece(move.getStartPosition()));
             }

             //Change the team turn.
             if(pieceOfInterest.getTeamColor() == TeamColor.BLACK){
                 setTeamTurn(TeamColor.WHITE);
             }else {
                 setTeamTurn(TeamColor.BLACK);
             }

             //Set the old position to null
             cur_board.addPiece(move.getStartPosition(), null);
         } else {
             throw new InvalidMoveException();
         }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingsPos = null;
        ChessMove posOfInterest;

        boolean $invalidState = false;


        if (teamColor == TeamColor.BLACK){
            //First search the board to find the king
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        continue;
                    }

                    if (cur_board.getPiece(new ChessPosition(i,j)).getPieceType() == ChessPiece.PieceType.KING &&
                    cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.BLACK){
                        kingsPos = new ChessPosition(i,j);
                        $invalidState = true;
                    }
                    if($invalidState){break;}
                }
                if($invalidState){break;}
            }
            //Now that you have found the king, see if any one from the white team can kill him.
            //If they can, return true. Otherwise, return false.
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        continue;
                    }
                    if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.WHITE){
                        //Get the possible moves of that piece
                        Collection<ChessMove> posMoves = new ChessPiece(cur_board.getPiece(new ChessPosition(i,j)).getTeamColor(),
                                cur_board.getPiece(new ChessPosition(i,j)).getPieceType()).pieceMoves(cur_board,new ChessPosition(i,j));
                        //Now go through each move's endposition and see if it matches the kings position
                        for (ChessMove posMove : posMoves) {

                            posOfInterest = posMove;

                            //If it matches return true
                            if (posOfInterest.getEndPosition().equals(kingsPos)) {
                                return true;
                            }
                        }
                    }
                }
            }


        } else if (teamColor == TeamColor.WHITE) {
            //First search the board to find the king
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        continue;
                    }
                    if (cur_board.getPiece(new ChessPosition(i,j)).getPieceType() == ChessPiece.PieceType.KING &&
                            cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.WHITE){
                        kingsPos = new ChessPosition(i,j);
                        $invalidState = true;
                    }
                    if($invalidState){break;}
                }if($invalidState){break;}
            }

            //Now that you have found the king, see if any one from the black team can kill him.
            //If they can, return true. Otherwise, return false.
            //First search the board to find the king
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        continue;
                    }
                    if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.BLACK){
                        //Get the possible moves of the piece.
                        Collection<ChessMove> posMoves = new ChessPiece(cur_board.getPiece(new ChessPosition(i,j)).getTeamColor(),
                                cur_board.getPiece(new ChessPosition(i,j)).getPieceType()).pieceMoves(cur_board,new ChessPosition(i,j));
                        //Now go through each move's endposition and see if it matches the kings position
                        for (ChessMove posMove : posMoves) {

                            posOfInterest = posMove;

                            //If it matches return true
                            if (posOfInterest.getEndPosition().equals(kingsPos)) {
                                return true;
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //Stores any valid move that brings the king out of check
        ArrayList<Collection<ChessMove>> totalMoves = new ArrayList<>();

        //This is to hold the number of valid moves.
        int numValidMoves = 0;

        //This is to be used to iterate through total moves and find the number of validmoves available.
        Collection<ChessMove> placeholder;

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if((cur_board.getPiece(new ChessPosition(i,j)) == null) ||
                        ((cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor))){
                    continue;
                } else if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor){
                    Collection<ChessMove> moves = validMoves(new ChessPosition(i,j));
                    totalMoves.add(moves);
                }
            }
        }
        for (Collection<ChessMove> totalMove : totalMoves) {
            placeholder = totalMove;
            numValidMoves += placeholder.size();

        }

        return (numValidMoves == 0) && isInCheck(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //Stores any valid move that brings the king out of check
        ArrayList<Collection<ChessMove>> totalMoves = new ArrayList<>();

        //This is to hold the number of valid moves.
        int numValidMoves = 0;

        //This is to be used to iterate through total moves and find the number of validmoves available.
        Collection<ChessMove> placeholder;

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if((cur_board.getPiece(new ChessPosition(i,j)) == null) ||
                        ((cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor))){
                    continue;
                } else if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor){
                    Collection<ChessMove> moves = validMoves(new ChessPosition(i,j));
                    totalMoves.add(moves);
                }
            }
        }
        for (Collection<ChessMove> totalMove : totalMoves) {
            placeholder = totalMove;
            numValidMoves += placeholder.size();

        }

        return (numValidMoves == 0) && (!isInCheck(teamColor));
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        cur_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return cur_board;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
