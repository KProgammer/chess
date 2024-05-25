package service;

import java.util.Random;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class CreateGamesService {
    private final CreateGameRequest request;

    public CreateGamesService(CreateGameRequest request){
        this.request = request;
    }

    //CreateGame
    public CreateGameResult CreateGame(){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new CreateGameResult(null,null,"Error: unauthorized");
        }

        int gameID = createGameID();
        gameObject.createGame(gameID, request.getGameName());

        return new CreateGameResult("gameID",gameID, request.getGameName());
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
