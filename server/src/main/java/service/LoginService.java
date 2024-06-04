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
            if((userObject.getUser(request.getUsername()) == null) ||
                    (!verifyUser(request.getUsername(), request.getPassword()))){
                return new LoginResult(null,null,"Error: unauthorized");
            }
            String authToken = authorizationObject.createAuth(request.getUsername());

            return new LoginResult(request.getUsername(),authToken, null);
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in login");
        }
        return null;
    }

    private boolean verifyUser(String username, String providedClearTextPassword) {
        String hashedPassword = null;
        // read the previously hashed password from the database
        try {
            hashedPassword = userObject.getUser(username).password();
        } catch (Exception e){
            System.out.println("Threw Runtime Error in verifyUser");
        }

        if(hashedPassword == null){
            return false;
        } else {
            return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
        }
    }
}
