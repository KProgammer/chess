package service;

import Results.ClearResult;

import static server.Server.*;

public class ClearService {
    public ClearResult clear(){

        authorizationObject.clear();
        gameObject.clear();
        userObject.clear();

        return new ClearResult(null);
    }
}
