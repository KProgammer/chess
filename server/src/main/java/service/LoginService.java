package service;

import java.util.Objects;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class LoginService {
    //login
    public RegisterResult login(RegisterRequest request){
        if(userObject.getUser(request.getUsername()) == null ||
                !Objects.equals(userObject.getUser(request.getUsername()).password(), request.getPassword())){
            return new RegisterResult(null,null,"Error: unauthorized");
        }

        return new RegisterResult(request.getUsername(),authorizationObject.createAuth(request.getUsername()),null);
    }
}
