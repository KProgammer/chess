package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{

    private final Integer gameID;
    private final ChessMove chessMove;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove chessMove) {
        super(authToken);
        this.gameID = gameID;
        this.chessMove = chessMove;
        this.commandType = CommandType.MAKE_MOVE;
    }

    public ChessMove getChessMove() {
        return chessMove;
    }

    public Integer getGameID() {
        return gameID;
    }
}
