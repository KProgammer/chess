package service;

import requests.ListGamesRequest;
import results.ListGamesResult;

import static server.Server.authorizationObject;
import static server.Server.gameObject;

public class ListGamesService {
    public ListGamesService() {
        super();
    }

    //ListGame
    public ListGamesResult makeList(ListGamesRequest request) {
        try {
            if (authorizationObject.getAuth(request.getAuthToken()) == null){
                return new ListGamesResult(null, "Error: unauthorized");
            }

            return new ListGamesResult(gameObject.listGames(), null);
        } catch (Exception e) {
            System.out.println("Threw RunTime Error in makeList.");
        }
        return null;
    }
}
