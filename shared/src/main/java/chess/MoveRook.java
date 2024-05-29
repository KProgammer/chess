package chess;

import java.util.ArrayList;

public class MoveRook extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> pieceMoves;
    private int posRow;
    private int posCol;
    private ChessGame curGame;

    public MoveRook(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves
        this.pieceMoves = new ArrayList<>();

        //Store the possible positions for the bishop
        this.posRow = position.getRow();
        this.posCol = position.getColumn();

        //Store the gameboard for the current game.
        this.curGame = new ChessGame();
        curGame.setBoard(board);
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        //Calculate moves for the right or towards col 8
        while(posCol < 8){
            //Add the values
            posCol += 1;

            // If the proposed space is not on the board, don't add it.
            if (!isOnBoard(new ChessPosition(posRow, posCol))){
                break;
            }

            //Attempt to add the move
            addPiece(pieceMoves, posRow, posCol);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(posRow, posCol)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        posRow = myPosition.getRow();
        posCol = myPosition.getColumn();

        //Calculate moves for downwards or towards row 1
        while(posRow > 1){
            //Add the values
            posRow -= 1;

            // If the proposed space is not on the board, don't add it.
            if (!isOnBoard(new ChessPosition(posRow, posCol))){
                break;
            }

            //Attempt to add the move
            addPiece(pieceMoves, posRow, posCol);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(posRow, posCol)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        posRow = myPosition.getRow();
        posCol = myPosition.getColumn();

        //Calculate moves for the left or towards col 1
        while(posCol > 1){
            //Add the values
            posCol -= 1;

            // If the proposed space is not on the board, don't add it.
            if (!isOnBoard(new ChessPosition(posRow, posCol))){
                break;
            }

            //Attempt to add the move
            addPiece(pieceMoves, posRow, posCol);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(posRow, posCol)) != null) {
                break;
            }
        }
        //Reset the values of pos_row and pos_col
        posRow = myPosition.getRow();
        posCol = myPosition.getColumn();

        //Calculate moves for upwards or towards row 8
        while(posRow < 8) {
            //Add the values
            posRow += 1;

            // If the proposed space is not on the board, don't add it.
            if (!isOnBoard(new ChessPosition(posRow, posCol))){
                break;
            }

            //Attempt to add the move
            addPiece(pieceMoves, posRow, posCol);

            //if there is any piece you must stop advancing
            if(board.getPiece(new ChessPosition(posRow, posCol)) != null) {
                break;
            }
        }

        return pieceMoves;
    }
}
