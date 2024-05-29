package service;

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



    @AfterEach
    public void clear(){
        new ClearService().clear();
    }

    @Test
    @Order(1)
    @DisplayName("ListGamesTestSuccess")
    public void SuccessListGameTest() {
        String authToken = authorizationObject.createAuth("practice");
        String authTokenLance = authorizationObject.createAuth("Lance");
        String authTokenIvan = authorizationObject.createAuth("Ivan");

        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",authToken));
        new CreateGamesService().CreateGame(new CreateGameRequest("LancesGame",authTokenLance));
        new CreateGamesService().CreateGame(new CreateGameRequest("IvansGame",authTokenIvan));

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
                "Not all games listed or not in the right order.");
    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedListGamesTest")
    public void UnauthorizedCreateGameTest() {
        Assertions.assertEquals(new ListGamesService().List(new ListGamesRequest(null)), new ListGamesResult(null,"Error: unauthorized"),
                "Not authorized error not thrown.");
    }
}
