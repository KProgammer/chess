package requests;

import chess.ChessGame;

import java.util.Objects;

public class JoinGameRequest {
    private String authToken;
    private final ChessGame.TeamColor playerColor;
    private final Integer gameID;

    public JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, Integer gameID){
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setAuthToken(String newAuthToken){
        authToken = newAuthToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest that = (JoinGameRequest) o;
        return Objects.equals(authToken, that.authToken) && playerColor == that.playerColor && Objects.equals(gameID, that.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, playerColor, gameID);
    }
}
