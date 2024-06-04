package chess;

import java.util.ArrayList;

public class MoveQueenRookBishop extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> pieceMoves;
    private int posRow;
    private int posCol;
    private ChessGame curGame;
    private ChessPiece chessPiece;

    public MoveQueenRookBishop (ChessBoard board, ChessPosition position, ChessPiece piece){
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
        this.chessPiece = piece;
    }

    public ArrayList<ChessMove> pieceMoves(ChessPosition myPosition) {

        Boolean statement = false;

        for (int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
                    statement = (posRow < 8 && posCol < 8 && Integer.signum(i) > 0 && Integer.signum(j) > 0) ||
                            (posCol < 8 && Integer.signum(i) == 0 && Integer.signum(j) > 0) ||
                            (posRow > 1 && posCol < 8 && Integer.signum(i) < 0 && Integer.signum(j) > 0) ||
                            (posRow > 1 && Integer.signum(i) < 0 && Integer.signum(j) == 0) ||
                            (posRow > 1 && posCol > 1 && Integer.signum(i) < 0 && Integer.signum(j) < 0) ||
                            (posCol > 1 && Integer.signum(i) == 0 && Integer.signum(j) < 0) ||
                            (posRow < 8 && posCol > 1 && Integer.signum(i) > 0 && Integer.signum(j) < 0) ||
                            (posRow < 8 && Integer.signum(i) > 0 && Integer.signum(j) == 0);
                } else if (chessPiece.getPieceType() == PieceType.BISHOP) {
                    statement = (posRow < 8 && posCol < 8 && Integer.signum(i) > 0 && Integer.signum(j) > 0) ||
                            (posRow > 1 && posCol < 8 && Integer.signum(i) < 0 && Integer.signum(j) > 0) ||
                            (posRow > 1 && posCol > 1 && Integer.signum(i) < 0 && Integer.signum(j) < 0) ||
                            (posRow < 8 && posCol > 1 && Integer.signum(i) > 0 && Integer.signum(j) < 0);
                } else if (chessPiece.getPieceType() == PieceType.ROOK){
                    statement = (posCol < 8 && Integer.signum(i) == 0 && Integer.signum(j) > 0) ||
                            (posRow > 1 && Integer.signum(i) < 0 && Integer.signum(j) == 0) ||
                            (posCol > 1 && Integer.signum(i) == 0 && Integer.signum(j) < 0) ||
                            (posRow < 8 && Integer.signum(i) > 0 && Integer.signum(j) == 0);
                }

                while(statement){
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

        return pieceMoves;
    }
}
