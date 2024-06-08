package serverfacade;

import chess.ChessGame;

public class JoinGameHelper {
    private ChessGame.TeamColor playerColor;
    private Integer gameID;

    public JoinGameHelper(ChessGame.TeamColor playerColor, Integer gameID){

        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }
}
