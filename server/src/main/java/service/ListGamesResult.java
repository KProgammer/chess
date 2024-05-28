package service;

import model.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ListGamesResult {
    private final Collection<Game> gameList;
    private final String message;

    public ListGamesResult(Collection<Game> gameList, String message){

        this.gameList = gameList;
        this.message = message;
    }

    public Collection<Game> getGameList() {
        return gameList;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesResult that = (ListGamesResult) o;
        return Objects.equals(gameList, that.gameList) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameList, message);
    }
}
