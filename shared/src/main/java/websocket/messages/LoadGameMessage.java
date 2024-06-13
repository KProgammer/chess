package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage {
    private final Integer gameID;
    public static ChessGame game;

    public LoadGameMessage(Integer gameID, ChessGame chessGame){
        this.gameID = gameID;
        this.game = chessGame;
    }

    public Integer getGameID() {
        return gameID;
    }

    public static ChessGame getGame() {
        return game;
    }
}
