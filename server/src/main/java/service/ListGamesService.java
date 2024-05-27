package service;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class ListGamesService {
    //ListGame
    public ListGamesResult List(ListGamesRequest request){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new ListGamesResult(null, "Error: unathorized");
        }

        return new ListGamesResult(gameObject.listGames(), null);
    }
}
