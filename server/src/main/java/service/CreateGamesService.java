package service;

import java.util.ArrayList;
import java.util.Collection;

public class CreateGamesService {


    //CreateGame
    public Collection<String> CreateGame(String authToken, String gameName){
        ArrayList<String> result = new ArrayList<>();

        if (authorizationObject.getAuth(authToken) == null){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        int gameID = createGameID();
        gameObject.createGame(gameID,gameName);

        result.add("'gameID':"+gameID);

        return result;
    }
}
