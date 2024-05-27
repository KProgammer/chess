package service;

import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class ListGamesTest {
    @Test
    @DisplayName("ListGamesTest")
    public void CreateGameTest() {
        String authToken = authorizationObject.createAuth("practice");
        String authTokenLance = authorizationObject.createAuth("Lance");
        String authTokenIvan = authorizationObject.createAuth("Ivan");

        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",authToken));
        new CreateGamesService().CreateGame(new CreateGameRequest("LancesGame",authToken));
        new CreateGamesService().CreateGame(new CreateGameRequest("IvansGame",authToken));

        int practiceGameID = gameObject.getGameID("practiceGame");
        int LancesGameID = gameObject.getGameID("LancesGame");
        int IvansGameID = gameObject.getGameID("IvansGame");



        new JoinGameService().JoinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK,practiceGameID));
        new JoinGameService().JoinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.WHITE,practiceGameID));
        new JoinGameService().JoinGame(new JoinGameRequest(authTokenIvan, ChessGame.TeamColor.BLACK,LancesGameID));
        new JoinGameService().JoinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.WHITE,LancesGameID));
        new JoinGameService().JoinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK,IvansGameID));
        new JoinGameService().JoinGame(new JoinGameRequest(authTokenIvan, ChessGame.TeamColor.WHITE,IvansGameID));

        Map<Integer, Game> mapGameList = new HashMap<>();
        mapGameList.put(practiceGameID,new Game(practiceGameID,"Lance","practice","practiceGame",new ChessGame()));
        mapGameList.put(LancesGameID, new Game(LancesGameID,"Lance","Ivan","LancesGame",new ChessGame()));
        mapGameList.put(IvansGameID,new Game(IvansGameID,"Ivan","practice","IvansGame",new ChessGame()));

        Collection<Game> gameList = new ArrayList<>();
        for(int game : mapGameList.keySet()){
            gameList.add(mapGameList.get(game));
        }

        Assertions.assertEquals(new ListGamesService().List(new ListGamesRequest(authToken)),new ListGamesResult(gameList,null),
                "Not all games listed");

        Assertions.assertEquals(new ListGamesService().List(new ListGamesRequest(null)), new ListGamesResult(null,"Error: unauthorized"),
                "Not authorized error not thrown.");
    }
}
