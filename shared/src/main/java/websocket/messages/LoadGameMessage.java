package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage {
    public static ChessGame game;
    public LoadGameMessage(ChessGame chessGame){
        this.game = chessGame;
    }
}
