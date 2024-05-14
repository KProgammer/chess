package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        } else{
            return new ChessPiece(cur_board.getPiece(startPosition).getTeamColor(),
                    cur_board.getPiece(startPosition).getPieceType()).pieceMoves(cur_board,startPosition);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
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
            //First search the board to find the king
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        continue;
                    }
                    if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.WHITE){
                        Collection<ChessMove> posMoves = new ChessPiece(cur_board.getPiece(new ChessPosition(i,j)).getTeamColor(),
                                cur_board.getPiece(new ChessPosition(i,j)).getPieceType()).pieceMoves(cur_board,new ChessPosition(i,j));
                        //Now go through each move's endposition and see if it matches the kings position
                        for (Iterator variable = posMoves.iterator(); variable.hasNext();){

                            posOfInterest = (ChessMove) variable.next();

                            //If it matches return true
                            if(posOfInterest.getEndPosition() == kingsPos){
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

            //Now that you have found the king, see if any one from the white team can kill him.
            //If they can, return true. Otherwise, return false.
            //First search the board to find the king
            for (int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    if(cur_board.getPiece(new ChessPosition(i,j)) == null){
                        break;
                    }
                    if (cur_board.getPiece(new ChessPosition(i,j)).getTeamColor() == TeamColor.BLACK){
                        Collection<ChessMove> posMoves = new ChessPiece(cur_board.getPiece(new ChessPosition(i,j)).getTeamColor(),
                                cur_board.getPiece(new ChessPosition(i,j)).getPieceType()).pieceMoves(cur_board,new ChessPosition(i,j));
                        //Now go through each move's endposition and see if it matches the kings position
                        for (Iterator variable = posMoves.iterator(); variable.hasNext();){

                            posOfInterest = (ChessMove) variable.next();

                            //If it matches return true
                            if(posOfInterest.getEndPosition() == kingsPos){
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
