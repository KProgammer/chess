package service;

import requests.CreateGameRequest;
import results.CreateGameResult;

import java.util.Random;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGamesService {
    //CreateGame
    public CreateGameResult createGame(CreateGameRequest request){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new CreateGameResult(null,"Error: unauthorized");
        } else if (request.getGameName() == null) {
            return new CreateGameResult(null,"Error: bad request");
        }

        int gameID = createGameID();
        gameObject.createGame(gameID, request.getGameName());

        return new CreateGameResult(gameID, request.getGameName());
    }

    private int createGameID(){
        Random rand = new Random();
        int min = 1000;
        int max = 10000;
        int gameID = (rand.nextInt(max-min+1)+min);

        //Make sure that the authToken is unique.
        while(gameObject.getGame(gameID) != null) {
            gameID = (rand.nextInt(max-min+1)+min);
        }

        return gameID;
    }

}
