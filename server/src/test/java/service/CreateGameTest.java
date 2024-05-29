package service;

import Requests.CreateGameRequest;
import Results.CreateGameResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGameTest {
    @Test
    @Order(1)
    @DisplayName("CreateGameTest")
    public void CreateGameTest1() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",authToken));

        int gameID = gameObject.getGameID("practiceGame");

        Assertions.assertEquals(gameObject.getGame(gameID).gameName(), "practiceGame",
                        "Game wasn't created");
    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedCreation")
    public void UnauthorizedCreateGame() {
        Assertions.assertEquals(new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",null)),
                new CreateGameResult(null,"Error: unauthorized"),"Unauthorized Error wasn't thrown");
    }

    @Test
    @Order(3)
    @DisplayName("BadRequestCreation")
    public void BadRequestCreateGame() {
        String authToken = authorizationObject.createAuth("practice");

        Assertions.assertEquals(new CreateGamesService().CreateGame(new CreateGameRequest(null,authToken)),
                new CreateGameResult(null,"Error: bad request"),"Error wasn't thrown");
    }
}
