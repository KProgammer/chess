package service;

import dataaccess.AuthorizationDAO;
import dataaccess.UserDAO;
import model.Authorization;
import model.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserService {
    private final UserDAO userObject;
    private final AuthorizationDAO authorizationObject;
    //UserDAO userObject = new UserDAO();
    //AuthorizationDAO authorizationObject = new AuthorizationDAO();

    public UserService(UserDAO userObject, AuthorizationDAO authorizationObject){

        this.userObject = userObject;
        this.authorizationObject = authorizationObject;
    }

    public void clear() {
        userObject.clear();
    }

    //Register
    public Collection<String> register(String username, String password, String email) {
        ArrayList<String> result = new ArrayList<>();

        if (userObject.getUser(username) != null){
            result.add("'message':'Error: already taken'");
            return result;
        }

        userObject.createUser(username,password,email);
        String authToken = authorizationObject.createAuth(username);

        result.add("'"+username+"':'");
        result.add("'"+authToken+"':'");
        return result;
    }
}
