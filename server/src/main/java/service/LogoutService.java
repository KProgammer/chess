package service;

import Requests.LogoutRequest;
import Results.LogoutResult;

import static server.Server.authorizationObject;

public class LogoutService {
    //logout
    public LogoutResult logout(LogoutRequest request){
        if(authorizationObject.getAuth(request.getAuthToken()) == null){
            return new LogoutResult("Error: unauthorized");
        }

        authorizationObject.deleteAuth(request.getAuthToken());

        return new LogoutResult(null);
    }
}
