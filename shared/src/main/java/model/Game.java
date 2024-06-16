package model;

import chess.ChessGame;

import java.util.Objects;

public record Game(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game1 = (Game) o;
        return Objects.equals(gameID, game1.gameID) && Objects.equals(game, game1.game) && Objects.equals(gameName, game1.gameName) && Objects.equals(whiteUsername, game1.whiteUsername) && Objects.equals(blackUsername, game1.blackUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game);
    }
}
