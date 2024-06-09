package service;

import requests.CreateGameRequest;
import requests.ListGamesRequest;
import results.CreateGameResult;
import results.ListGamesResult;

import java.util.Random;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGamesService {
    //CreateGame
    public CreateGameResult createGame(CreateGameRequest request) {
        try {
            if ((request.getAuthToken() == null) ||
                    (authorizationObject.getAuth(request.getAuthToken()).authToken() == null) ||
                    (authorizationObject.getAuth(request.getAuthToken()).username() == null)) {
                return new CreateGameResult(null, "Error: unauthorized");
            } else if ((request.getGameName() == null)){// || (gameObject.getGameID(request.getGameName()) != null)) {
                return new CreateGameResult(null, "Error: bad request");
            }

            ListGamesResult listGamesResult = new ListGamesService().makeList(new ListGamesRequest(request.getAuthToken()));
            if(listGamesResult.getGames().size() > 1000){
                System.out.println("Too many games. Cannot create another one.");
                return new CreateGameResult(null, "Error: bad request");
            }

            Integer gameID = createGameID();
            gameObject.createGame(gameID, request.getGameName());

            return new CreateGameResult(gameID, request.getGameName());
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in createGame.");
        }
        return null;
    }

    private Integer createGameID() {
        Random rand = new Random();
        int min = 1000;
        int max = 10000;
        int gameID = (rand.nextInt(max-min+1)+min);
        //int gameID = rand.nextInt();

        //Make sure that the gameID is unique.
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
