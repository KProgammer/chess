package chess;

import java.util.ArrayList;

public class MoveKnight extends ChessPiece {
    private final ChessBoard board;
    private final ChessPosition position;
    private ArrayList<ChessMove> Piece_Moves;
    private int pos_row;
    private int pos_col;
    private ChessGame cur_game;
    private ChessPiece knight;

    public MoveKnight(ChessBoard board, ChessPosition position){
        super(board,position);

        this.board = board;
        this.position = position;

        //Stores the list of moves the king can make
        this.Piece_Moves = new ArrayList<>();

        // Stores the possible positions for each piece
        this.pos_row= position.getRow();
        this.pos_col = position.getColumn();

        //This is used to track the current game
        this.cur_game = new ChessGame();
        cur_game.setBoard(this.board);

        //This creates a chesspiece that refects what you are interested in.
        this.knight = new ChessPiece(board.getPiece(position).getTeamColor(), ChessPiece.PieceType.KNIGHT);
    }

    /**
     * Calculates all moves the knight can make
     *
     * @param myPosition The starting position of the knight
     * @return A list of all the possible moves the knight can make
     */
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
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
                //Reset the values of pos_row and pos_col
                pos_row = myPosition.getRow();
                pos_col = myPosition.getColumn();

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

                //Attempt to add the move
                AddPiece(pos_row,pos_col);
            }
        }

        return Piece_Moves;
    }

    /**
     * Adds a move to the list of valid moves
     *
     * @param move The position the knight is to be placed
     * @return If there should be no more moves in the direction of the given move, this will return false
     */
    /*private boolean AddMove(ChessPosition move) {
        //Create a boolean to determine if the space being considered is empty
        boolean isEmpty = false;

        //These are if the king is in check.
        ChessGame possibleGame = new ChessGame();
        ChessBoard possibleBoard = cur_game.getBoard().clone();
        ChessMove possibleMove;
        possibleGame.setBoard(possibleBoard);

        //Set pos_row and pos_col to starting position
        pos_row = position.getRow();
        pos_col = position.getColumn();

        //If the space is empty, add it as a possible move.
        if (board.getPiece(move) == null) {
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(board.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), board.getPiece(position));

                //See if the king is still in check.
                if (possibleGame.isInCheck(possibleBoard.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Add the position to the new list of possible moves.
            Piece_Moves.add(new ChessMove(position, move, null));
            isEmpty = true;
            return isEmpty;
        }
        //If the space contains an opposing piece, add the space as a possible move and then end all
        //possible moves in this direction.
        else if (board.getPiece(move).getTeamColor() != board.getPiece(position).getTeamColor()) {
            //If the king is in check see if this move will stop the board from being in check
            if(cur_game.isInCheck(board.getPiece(position).getTeamColor()));{
                //Add the move possible move
                possibleMove = new ChessMove(position,move,null);
                //Clear both squares
                possibleBoard.addPiece(position,null);
                possibleBoard.addPiece(possibleMove.getEndPosition(),null);
                //Set the piece in the new position
                possibleBoard.addPiece(possibleMove.getEndPosition(), board.getPiece(move));

                //See if the king is still in check.
                if (possibleGame.isInCheck(board.getPiece(move).getTeamColor())){
                    isEmpty = true;
                    return isEmpty;
                }
                //If not continue
            }

            //Add the position to the new list of possible moves.
            Piece_Moves.add(new ChessMove(position, move, null));
            return isEmpty;
        }
        //If the space contains a piece on the same team, end all possible moves in this direction.
        else {
            return isEmpty;
        }
    }*/
}
