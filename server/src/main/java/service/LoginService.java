package service;

import spark.Response;

import java.util.Objects;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class LoginService {
    /*public LoginService() {
        super();
    }*/

    //login
    public LoginResult login(LoginRequest request){
        if(userObject.getUser(request.getUsername()) == null ||
                !Objects.equals(userObject.getUser(request.getUsername()).password(), request.getPassword())){
            return new LoginResult(null,null,"Error: unauthorized");
        }
        String authToken = authorizationObject.createAuth(request.getUsername());

        return new LoginResult(request.getUsername(), authToken, null);
    }
}
