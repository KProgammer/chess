package service;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import results.ListGamesResult;
import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class ListGamesTest {

    @Test
    @Order(1)
    @DisplayName("ListGamesTestSuccess")
    public void successListGameTest() {
        try {
            new ClearService().clear();

            String authToken = authorizationObject.createAuth("practice");
            String authTokenLance = authorizationObject.createAuth("Lance");
            String authTokenIvan = authorizationObject.createAuth("Ivan");

            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));
            new CreateGamesService().createGame(new CreateGameRequest("LancesGame", authTokenLance));
            new CreateGamesService().createGame(new CreateGameRequest("IvansGame", authTokenIvan));

            int practiceGameID = gameObject.getGameID("practiceGame");
            int lancesGameID = gameObject.getGameID("LancesGame");
            int ivansGameID = gameObject.getGameID("IvansGame");

            new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK, practiceGameID));
            new JoinGameService().joinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.WHITE, practiceGameID));
            new JoinGameService().joinGame(new JoinGameRequest(authTokenIvan, ChessGame.TeamColor.BLACK, lancesGameID));
            new JoinGameService().joinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.WHITE, lancesGameID));
            new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK, ivansGameID));
            new JoinGameService().joinGame(new JoinGameRequest(authTokenIvan, ChessGame.TeamColor.WHITE, ivansGameID));

            Map<Integer, Game> mapGameList = new HashMap<>();
            mapGameList.put(practiceGameID, new Game(practiceGameID, "Lance", "practice", "practiceGame", new ChessGame()));
            mapGameList.put(lancesGameID, new Game(lancesGameID, "Lance", "Ivan", "LancesGame", new ChessGame()));
            mapGameList.put(ivansGameID, new Game(ivansGameID, "Ivan", "practice", "IvansGame", new ChessGame()));

            Collection<Game> gameList = new ListGamesService().makeList(new ListGamesRequest(authToken)).getGames();
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
        } catch (Exception e){
            System.out.println("Threw Runtime Error in successListGameTest");
        }
    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedListGamesTest")
    public void unauthorizedCreateGameTest() {
        try {
            Assertions.assertEquals(new ListGamesService().makeList(new ListGamesRequest(null)), new ListGamesResult(null,"Error: unauthorized"),
                    "Not authorized error not thrown.");
        } catch (Exception e) {
            System.out.println("Threw Runtime Exception in unauthorizedCreateGameTest");
        }
    }
}
