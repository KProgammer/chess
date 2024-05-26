package service;

import model.Game;

import java.util.ArrayList;
import java.util.Collection;

public class ListGamesResult {
    private final Collection<Game> gameList;
    private final String message;

    public ListGamesResult(Collection<Game> gameList, String message){

        this.gameList = gameList;
        this.message = message;
    }
}
