package service;

import dataaccess.*;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import results.CreateGameResult;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGameTest {

    @Test
    @Order(1)
    @DisplayName("CreateGameTest")
    public void createGameTest1() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");
            new CreateGamesService().createGame(new CreateGameRequest("practiceGame", authToken));

            int gameID = gameObject.getGameID("practiceGame");

            Assertions.assertEquals(gameObject.getGame(gameID).gameName(), "practiceGame",
                    "Game wasn't created");
        } catch (Exception e){
            System.out.println("Threw Runtime Error in createGameTest1");
            throw e;
        }
    }

    @Test
    @Order(2)
    @DisplayName("UnauthorizedCreation")
    public void unauthorizedCreateGame() {
        try {
            Assertions.assertEquals(new CreateGamesService().createGame(new CreateGameRequest("practiceGame",null)),
                    new CreateGameResult(null,"Error: unauthorized"),"Unauthorized Error wasn't thrown");
        } catch (Exception e) {
            System.out.print("Threw RunTime Error in unauthorizedCreateGame!");
            throw e;
        }
    }

    @Test
    @Order(3)
    @DisplayName("BadRequestCreation")
    public void badRequestCreateGame() throws Exception {
        try {
            String authToken = authorizationObject.createAuth("practice");

            Assertions.assertEquals(new CreateGamesService().createGame(new CreateGameRequest(null, authToken)),
                    new CreateGameResult(null, "Error: bad request"), "Error wasn't thrown");
        } catch (Exception e){
            System.out.print("Threw Runtime Error in badRequestCreateGame");
            throw e;
        }
    }
}
