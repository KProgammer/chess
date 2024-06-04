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
    public void joinGameAsBlackTest() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");
            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));

            int gameID = gameObject.getGameID("practiceGame");
            new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK, gameID));

            Assertions.assertEquals(gameObject.getGame(gameID).blackUsername(), "practice",
                    "practice didn't join the game as the black team");
        } catch (Exception e){
            System.out.println("Threw Runtime Error joinGameAsBlackTest");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("JoinGameAsWhiteTest")
    public void joinGameAsWhiteTest() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");
            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));
            int gameID = gameObject.getGameID("practiceGame");

            new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID));

            Assertions.assertEquals(gameObject.getGame(gameID).whiteUsername(), "practice",
                    "practice didn't join the game as the white team");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in joinGameAsWhiteTest");
            throw e;
        }
    }

    @Test
    @Order(3)
    @DisplayName("JoinGameUnauthorized")
    public void joinGameUnauthorizedTest() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");
            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));
            int gameID = gameObject.getGameID("practiceGame");

            Assertions.assertEquals(new JoinGameService().joinGame(new JoinGameRequest(null, ChessGame.TeamColor.BLACK, gameID)),
                    new JoinGameResult("Error: unauthorized"), "Unauthorized Error wasn't thrown");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in joinGameUnauthorizedTest");
            throw e;
        }
    }

    @Test
    @Order(4)
    @DisplayName("JoinAlreadyTakenGame")
    public void joinGameAlreadyTakenTest() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");
            String authTokenLance = authorizationObject.createAuth("Lance");
            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));

            int gameID = gameObject.getGameID("practiceGame");
            new JoinGameService().joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.BLACK, gameID));

            Assertions.assertEquals(new JoinGameService().joinGame(new JoinGameRequest(authTokenLance, ChessGame.TeamColor.BLACK, gameID)),
                    new JoinGameResult("Error: already taken"), "Already taken error wasn't thrown");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in joinGameAlreadyTakenTest");
            throw e;
        }
    }
}
