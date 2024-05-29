package chess;

import java.util.ArrayList;

public class MoveQueen extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> pieceMoves;
    private int posRow;
    private int posCol;
    private ChessGame curGame;
    private ChessPiece queen;

    public MoveQueen(ChessBoard board, ChessPosition position){
        super(board, position);

        this.board = board;
        this.position = position;

        //Stores the list of moves
        this.pieceMoves = new ArrayList<>();

        //Store the possible positions for the bishop
        this.posRow = position.getRow();
        this.posCol = position.getColumn();

        //Stores teh current chessgame
        this.curGame = new ChessGame();
        curGame.setBoard(board);

        //This piece stores a piece similar to what we are interested in
        this.queen = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.QUEEN);
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        for (int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                while((posRow < 8 && posCol < 8 && Integer.signum(i) > 0 && Integer.signum(j) > 0) ||
                        (posCol < 8 && Integer.signum(i) == 0 && Integer.signum(j) > 0) ||
                        (posRow > 1 && posCol < 8 && Integer.signum(i) < 0 && Integer.signum(j) > 0) ||
                        (posRow > 1 && Integer.signum(i) < 0 && Integer.signum(j) == 0) ||
                        (posRow > 1 && posCol > 1 && Integer.signum(i) < 0 && Integer.signum(j) < 0) ||
                        (posCol > 1 && Integer.signum(i) == 0 && Integer.signum(j) < 0) ||
                        (posRow < 8 && posCol > 1 && Integer.signum(i) > 0 && Integer.signum(j) < 0) ||
                        (posRow < 8 && Integer.signum(i) > 0 && Integer.signum(j) == 0)){
                    //Add the values
                    posRow += i;
                    posCol += j;

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
            }
        }


        /*//Calculate moves for the upper right diagonal or towards row 8, col 8
        while(posRow < 8 && posCol < 8){
            //Add the values
            posRow += 1;
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

        //Calculate moves for the lower right diagonal or towards row 1, col 8
        while(posRow > 1 && posCol < 8){
            //Add the values
            posRow -= 1;
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

        //Calculate moves for down or towards row 1
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

        //Calculate moves for the lower left diagonal or towards row 1, col 1
        while(posRow > 1 && posCol > 1){
            //Add the values
            posRow -= 1;
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

        //Calculate moves for the upper left diagonal or towards row 8, col 1
        while(posRow < 8 && posCol > 1) {
            //Add the values
            posRow += 1;
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
        while(posRow < 8){
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
        }*/

        return pieceMoves;
    }
}
