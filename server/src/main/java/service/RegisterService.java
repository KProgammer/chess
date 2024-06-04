package service;

import org.mindrot.jbcrypt.BCrypt;
import requests.RegisterRequest;
import results.RegisterResult;

import static server.Server.authorizationObject;
import static server.Server.userObject;

public class RegisterService {
    
    //Register
    public RegisterResult register(RegisterRequest request) {
        try {
            if (userObject.getUser(request.getUsername()).username() != null){
                return new RegisterResult(null, null,"Error: already taken");
            } else if (request.getUsername() == null ||
                        request.getPassword() == null ||
                        request.getEmail() == null) {
                return new RegisterResult(null, null,"Error: bad request");
            }

            String hashPassword = BCrypt.hashpw(request.getPassword(),BCrypt.gensalt());

            userObject.createUser(request.getUsername(), hashPassword,request.getEmail());
            String authToken = authorizationObject.createAuth(request.getUsername());

            return new RegisterResult(request.getUsername(), authToken,null);
        } catch (Exception e) {
            System.out.println("Threw Runtime Error in register");
        }
        return null;
    }
}
