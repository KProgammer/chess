package service;

import chess.ChessGame;

public class JoinGameRequest {
    private final String authToken;
    private final ChessGame.TeamColor teamColor;
    private final Integer gameID;

    public JoinGameRequest(String authToken, ChessGame.TeamColor teamColor, Integer gameID){
        this.authToken = authToken;
        this.teamColor = teamColor;
        this.gameID = gameID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    public Integer getGameID() {
        return gameID;
    }
}
