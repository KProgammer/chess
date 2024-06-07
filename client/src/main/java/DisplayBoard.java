import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DisplayBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static int pos_row = 1;
    private static int pos_col = 1;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static Random rand = new Random();


    public static void main(String[] args, ChessGame.TeamColor teamColor, ChessGame chessGame) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, teamColor);

        drawTicTacToeBoard(out, chessGame, teamColor);

        drawHeaders(out, teamColor);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, ChessGame.TeamColor teamColor) {

        setBlack(out);

        String[] headers = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};

        if(teamColor == ChessGame.TeamColor.BLACK ) {
            headers = new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};
        }

        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawTicTacToeBoard(PrintStream out, ChessGame chessGame, ChessGame.TeamColor teamColor) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, chessGame, teamColor);
            pos_row += 1;
        }
    }

    private static void drawRowOfSquares(PrintStream out, ChessGame chessGame, ChessGame.TeamColor teamColor) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                out.print(SET_TEXT_COLOR_GREEN);
                out.print(" "+pos_row+" ");
            }
            else {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {

                setWhite(out);

                pos_col = (boardCol + 1);

                if((boardCol%2 == 0) && (pos_row%2 == 0) ||
                        (boardCol%2 != 0 && (pos_row%2 != 0))){
                    setBlack(out);
                }

                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    if(chessGame.getBoard().getPiece(new ChessPosition(pos_row,pos_col)) != null) {
                        printPlayer(out, PieceSymbol(chessGame.getBoard().getPiece(new ChessPosition(pos_row, pos_col))),
                                chessGame.getBoard().getPiece(new ChessPosition(pos_row, pos_col)).getTeamColor());
                    } else {
                        printPlayer(out, "   ", null);
                    }
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
                setBlack(out);
            }
            out.println();
        }
    }

    private static void drawVerticalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
            setRed(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));

            setBlack(out);
            out.println();
        }
    }

    private static String PieceSymbol(ChessPiece chessPiece){
        if (chessPiece == null){
            return "   ";
        } else if(chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
            return " Q ";
        } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
            return " K ";
        } else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            return " B ";
        } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return " N ";
        } else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return " R ";
        } else {
            return " P ";
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player, ChessGame.TeamColor teamColor) {
        //out.print(SET_BG_COLOR_WHITE);

        if (teamColor == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_COLOR_RED);
        } else {
            out.print(SET_TEXT_COLOR_LIGHT_GREY);
        }

        out.print(player);

        //setWhite(out);
    }
}
