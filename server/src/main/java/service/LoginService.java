package service;

import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import results.LoginResult;

import java.util.Objects;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class LoginService {

    //login
    public LoginResult login(LoginRequest request) {
        try {
            if(userObject.getUser(request.getUsername()) == null ||
                    !Objects.equals(userObject.getUser(request.getUsername()).password(),
                            BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()))){
                return new LoginResult(null,null,"Error: unauthorized");
            }
            String authToken = authorizationObject.createAuth(request.getUsername());

            return new LoginResult(request.getUsername(),authToken, null);
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in login");
        }
        return null;
    }
}
