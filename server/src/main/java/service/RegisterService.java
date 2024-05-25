package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class RegisterService {
    
    //Register
    public RegisterResult register(RegisterRequest request) {
        if (userObject.getUser(request.getUsername()) != null){
            return new RegisterResult(null, null,"Error: already taken");
        }

        userObject.createUser(request.getUsername(), request.getPassword(),request.getEmail());
        String authToken = authorizationObject.createAuth(request.getUsername());

        return new RegisterResult(request.getUsername(), authToken,null);
    }
}