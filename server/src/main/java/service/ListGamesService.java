package service;

import Requests.ListGamesRequest;
import Results.ListGamesResult;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class ListGamesService {
    public ListGamesService() {
        super();
    }

    //ListGame
    public ListGamesResult List(ListGamesRequest request){
        if (authorizationObject.getAuth(request.getAuthToken()) == null){
            return new ListGamesResult(null, "Error: unauthorized");
        }

        return new ListGamesResult(gameObject.listGames(), null);
    }
}
