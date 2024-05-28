package service;

import model.Game;

import java.util.Collection;
import java.util.Objects;

public class ListGamesResult {
    private final Collection<Game> games;
    private final String message;

    public ListGamesResult(Collection<Game> games, String message){

        this.games = games;
        this.message = message;
    }

    public Collection<Game> getGames() {
        return games;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesResult that = (ListGamesResult) o;
        return Objects.equals(games, that.games) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(games, message);
    }
}
