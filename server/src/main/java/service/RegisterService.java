package service;

import requests.RegisterRequest;
import results.RegisterResult;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class RegisterService {
    
    //Register
    public RegisterResult register(RegisterRequest request) {
        if (userObject.getUser(request.getUsername()) != null){
            return new RegisterResult(null, null,"Error: already taken");
        } else if (request.getUsername() == null ||
                    request.getPassword() == null ||
                    request.getEmail() == null) {
            return new RegisterResult(null, null,"Error: bad request");
        }

        userObject.createUser(request.getUsername(), request.getPassword(),request.getEmail());
        String authToken = authorizationObject.createAuth(request.getUsername());

        return new RegisterResult(request.getUsername(), authToken,null);
    }
}
