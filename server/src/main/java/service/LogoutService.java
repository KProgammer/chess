package service;

import requests.LogoutRequest;
import results.LogoutResult;

import static server.Server.authorizationObject;

public class LogoutService {
    //logout
    public LogoutResult logout(LogoutRequest request) {
        try {
            if((request.getAuthToken() == null)||
            (authorizationObject.getAuth(request.getAuthToken()) == null)){
                return new LogoutResult("Error: unauthorized");
            }

            authorizationObject.deleteAuth(request.getAuthToken());
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in logout");
        }

        return new LogoutResult(null);
    }
}
