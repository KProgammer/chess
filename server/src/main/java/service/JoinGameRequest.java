package service;

import chess.ChessGame;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest that = (JoinGameRequest) o;
        return Objects.equals(authToken, that.authToken) && teamColor == that.teamColor && Objects.equals(gameID, that.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, teamColor, gameID);
    }
}
