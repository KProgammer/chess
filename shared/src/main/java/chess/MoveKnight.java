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
                AddPiece(Piece_Moves,pos_row,pos_col);
            }
        }

        return Piece_Moves;
    }
}
