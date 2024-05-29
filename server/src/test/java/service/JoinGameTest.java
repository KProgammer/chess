package service;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.JoinGameResult;
import chess.ChessGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class JoinGameTest {
    @Test
    @Order(1)
    @DisplayName("JoinGameAsBlack")
    public void joinGameAsBlackTest() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().createGame(new CreateGameRequest("practiceGame",authToken));

        int gameID = gameObject.getGameID("practiceGame");
        new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK,gameID));

        Assertions.assertEquals(gameObject.getGame(gameID).blackUsername(), "practice",
                "practice didn't join the game as the black team");
    }

    @Test
    @Order(2)
    @DisplayName("JoinGameAsWhiteTest")
    public void joinGameAsWhiteTest() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().createGame(new CreateGameRequest("practiceGame",authToken));
        int gameID = gameObject.getGameID("practiceGame");

        new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE,gameID));

        Assertions.assertEquals(gameObject.getGame(gameID).whiteUsername(), "practice",
                "practice didn't join the game as the white team");
    }

    @Test
    @Order(3)
    @DisplayName("JoinGameUnauthorized")
    public void joinGameUnauthorizedTest() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().createGame(new CreateGameRequest("practiceGame",authToken));
        int gameID = gameObject.getGameID("practiceGame");

        Assertions.assertEquals(new JoinGameService().joinGame(new JoinGameRequest(null, ChessGame.TeamColor.BLACK, gameID)),
                new JoinGameResult("Error: unauthorized"),"Unauthorized Error wasn't thrown");
    }

    @Test
    @Order(4)
    @DisplayName("JoinAlreadyTakenGame")
    public void joinGameAlreadyTakenTest() {
        String authToken = authorizationObject.createAuth("practice");
        String authTokenLance = authorizationObject.createAuth("Lance");
        new CreateGamesService().createGame(new CreateGameRequest("practiceGame",authToken));

        int gameID = gameObject.getGameID("practiceGame");
        new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK,gameID));

        Assertions.assertEquals(new JoinGameService().joinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.BLACK, gameID)),
                new JoinGameResult("Error: already taken"),"Already taken error wasn't thrown");
    }
}
