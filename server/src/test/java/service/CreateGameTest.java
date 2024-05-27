package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGameTest {
    @Test
    @DisplayName("CreateGameTest")
    public void CreateGameTest() {
        String authToken = authorizationObject.createAuth("practice");
        new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",authToken));

        int gameID = gameObject.getGameID("practiceGame");

        Assertions.assertEquals(gameObject.getGame(gameID).gameName(), "practiceGame",
                        "Game wasn't created");

        Assertions.assertEquals(new CreateGamesService().CreateGame(new CreateGameRequest("practiceGame",null)),
                new CreateGameResult(null,null,"Error: unauthorized"),"Unauthorized Error wasn't thrown");

        /*Assertions.assertEquals(new CreateGamesService().CreateGame(new CreateGameRequest(null,authToken)),
                "Error: bad request","Error wasn't thrown");*/
    }
}
