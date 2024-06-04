package dataaccess;

import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static server.Server.gameObject;

public class ListGamesTest {
    @Test
    @Order(1)
    @DisplayName("ListAllGamesTest")
    public void ListAllGamesTest () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(5555,"AnotherGame");
            gameObject.createGame(6666,"AnotherAnotherGame");
            gameObject.createGame(7777,"AnotherAnotherAnotherGame");

            Map<Integer, Game> mapGameList = new HashMap<>();
            mapGameList.put(5555, new Game(5555, null, null, "AnotherGame", new ChessGame()));
            mapGameList.put(6666, new Game(6666, null, null, "AnotherAnotherGame", new ChessGame()));
            mapGameList.put(7777, new Game(7777, null, null, "AnotherAnotherAnotherGame", new ChessGame()));

            Collection<Game> gameList = gameObject.listGames();
            int counter = 0;

            for (int gameid : mapGameList.keySet()) {
                for(Game game : gameList) {
                    if (game.equals(mapGameList.get(gameid))){
                        counter += 1;
                    }
                }
            }

            Assertions.assertEquals(counter ,3, "Not all games listed.");
            Assertions.assertEquals(gameList.size(),3, "More or less games than should be listed.");
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in ListAllGamesTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("ListAllGamesFail")
    public void ListAllGamesFail () throws Exception {
        try {
            gameObject.clear();
            gameObject.createGame(5555,"AnotherGame");
            gameObject.createGame(6666,"AnotherAnotherGame");

            Map<Integer, Game> mapGameList = new HashMap<>();
            mapGameList.put(5555, new Game(5555, null, null, "AnotherGame", new ChessGame()));
            mapGameList.put(6666, new Game(6666, null, null, "AnotherAnotherGame", new ChessGame()));

            Collection<Game> gameList = gameObject.listGames();
            int counter = 0;

            for (int gameid : mapGameList.keySet()) {
                for(Game game : gameList) {
                    if (game.equals(mapGameList.get(gameid))){
                        counter += 1;
                    }
                }
            }

            Assertions.assertEquals(counter ,2, "Not all games listed.");
            Assertions.assertEquals(gameList.size(),2, "More or less games than should be listed.");

            gameObject.createGame(7777,"AnotherAnotherAnotherGame");
            mapGameList.put(7777, new Game(7777, null, null, "AnotherAnotherAnotherGame", new ChessGame()));

            gameList = gameObject.listGames();

            counter = 0;
            for (int gameid : mapGameList.keySet()) {
                for(Game game : gameList) {
                    if (game.equals(mapGameList.get(gameid))){
                        counter += 1;
                    }
                }
            }

            Assertions.assertEquals(counter ,3, "Not all games listed.");
            Assertions.assertEquals(gameList.size(),3, "More or less games than should be listed.");

        } catch (Exception e) {
            System.out.println("Threw Runtime Error in GetAUserFail");
            throw e;
        }
    }
}
