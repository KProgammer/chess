package service;

import java.util.ArrayList;
import java.util.Collection;

import static server.Server.authorizationObject;

public class LogoutService {
    //logout
    public LogoutResult logout(LogoutRequest request){
        if(authorizationObject.getAuth(request.getAuthToken()) == null){
            return new LogoutResult("Error: unauthorized");
        }

        return new LogoutResult(null);
    }
}
