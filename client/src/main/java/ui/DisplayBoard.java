package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class DisplayBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static ArrayList<ChessMove> squaresToHighLight = new ArrayList<>();
    private static int posRow = 1;
    private static int posCol = 1;
    private static final String EMPTY = "   ";
    private static ChessPosition startPosition = null;


    public static void main(ChessGame.TeamColor teamColor, ChessGame chessGame, ChessPosition piecePos) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        if(piecePos != null) {
            if(chessGame.getBoard().getPiece(piecePos) == null){
                System.out.println("Cannot show legal moves for blank squares.");
                return;
            }
            else if (chessGame.getBoard().getPiece(piecePos) != null &&
                    chessGame.getBoard().getPiece(piecePos).getTeamColor() == teamColor) {
                squaresToHighLight = (ArrayList<ChessMove>) chessGame.validMoves(piecePos);
                startPosition = piecePos;
            } else {
                System.out.println("Can only show legal moves of the player whose turn it currently is.");
                return;
            }
        } else {
            squaresToHighLight.clear();
            startPosition = piecePos;
        }

        out.print(ERASE_SCREEN);

        drawHeaders(out, teamColor);

        drawChessBoard(out, chessGame, teamColor);

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

    private static void printSideBarText(Integer squareRow, ChessGame.TeamColor teamColor){
        if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" "+ posRow +" ");
        }
        else {
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }
    }

    private static void drawChessBoard(PrintStream out, ChessGame chessGame, ChessGame.TeamColor teamColor) {
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            if (teamColor == ChessGame.TeamColor.BLACK){
                posRow = (boardRow + 1);
            } else {
                posRow = (8-boardRow);
            }
            drawRowOfSquares(out, chessGame, teamColor);
        }
    }

    private static void drawRowOfSquares(PrintStream out, ChessGame chessGame, ChessGame.TeamColor teamColor) {
        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            printSideBarText(squareRow, teamColor);
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if(teamColor == ChessGame.TeamColor.WHITE) {
                    setBlack(out);
                } else {
                    setWhite(out);
                }

                if (teamColor == ChessGame.TeamColor.BLACK){
                    posCol = (8-boardCol);
                } else {
                    posCol = (boardCol + 1);
                }

                if((boardCol%2 == 0) && (posRow %2 == 0) ||
                        (boardCol%2 != 0 && (posRow %2 != 0))){
                    if(teamColor == ChessGame.TeamColor.WHITE) {
                        setWhite(out);
                    } else {
                        setBlack(out);
                    }
                }

                for(ChessMove move : squaresToHighLight){
                    if (move.getEndPosition().equals(new ChessPosition(posRow,posCol))){
                        setYellow(out);
                        break;
                    }
                }

                if (startPosition != null &&
                        startPosition.equals(new ChessPosition(posRow, posCol))){
                    setGreen(out);
                    //continue;
                }


                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    if(chessGame.getBoard().getPiece(new ChessPosition(posRow, posCol)) != null) {
                        printPlayer(out, pieceSymbol(chessGame.getBoard().getPiece(new ChessPosition(posRow, posCol))),
                                chessGame.getBoard().getPiece(new ChessPosition(posRow, posCol)).getTeamColor());
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
            printSideBarText(squareRow, teamColor);
            out.println();
        }
    }

    private static String pieceSymbol(ChessPiece chessPiece){
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

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setYellow(PrintStream out) {
        out.print(SET_BG_COLOR_YELLOW);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
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
