package service;

import requests.CreateGameRequest;
import results.CreateGameResult;

import java.util.Random;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGamesService {
    //CreateGame
    public CreateGameResult createGame(CreateGameRequest request) {
        try {
            if ((request.getAuthToken() == null)
                    || (authorizationObject.getAuth(request.getAuthToken()) == null)) {
                return new CreateGameResult(null, "Error: unauthorized");
            } else if (request.getGameName() == null) {
                return new CreateGameResult(null, "Error: bad request");
            }

            int gameID = createGameID();
            gameObject.createGame(gameID, request.getGameName());

            return new CreateGameResult(gameID, request.getGameName());
        } catch (Exception e) {
            System.out.println("Threw RunTime Error in Create Game.");
        }
        return null;
    }

    private int createGameID(    ) {
        Random rand = new Random();
        int min = 1000;
        int max = 10000;
        int gameID = (rand.nextInt(max-min+1)+min);

        //Make sure that the authToken is unique.
        while(true) {
            try {
                if (gameObject.getGame(gameID) != null) break;
            } catch (Exception e) {
                System.out.println("Threw RunTime Error in createGameID");
            }
            gameID = (rand.nextInt(max-min+1)+min);
        }

        return gameID;
    }

}
