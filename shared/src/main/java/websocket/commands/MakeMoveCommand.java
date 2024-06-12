package websocket.commands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{

    private final ChessGame chessGame;

    public MakeMoveCommand(String authToken, ChessGame chessGame) {
        super(authToken);
        this.chessGame = chessGame;
    }

    public ChessGame getChessGame() {
        return chessGame;
    }
}
