package service;

import results.ClearResult;

import static server.Server.*;

public class ClearService {
    public ClearResult clear(){

        try {
            authorizationObject.clear();
            gameObject.clear();
            userObject.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ClearResult(null);
    }
}
