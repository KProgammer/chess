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

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        //Calculate moves for the right or towards col 8
        while(pos_col < 8){
            //Add the values
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

        //Calculate moves for downwards or towards row 1
        while(pos_row > 1){
            //Add the values
            pos_row -= 1;

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

        //Calculate moves for the left or towards col 1
        while(pos_col > 1){
            //Add the values
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

        //Calculate moves for upwards or towards row 8
        while(pos_row < 8) {
            //Add the values
            pos_row += 1;

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
}
