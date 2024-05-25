package service;

import dataaccess.AuthorizationMemoryDAO;
import dataaccess.UserMemoryDAO;

import java.util.ArrayList;
import java.util.Collection;

public class UserService {
    private final UserMemoryDAO userObject;
    private final AuthorizationMemoryDAO authorizationObject;
    //UserDAO userObject = new UserDAO();
    //AuthorizationDAO authorizationObject = new AuthorizationDAO();

    public UserService(UserMemoryDAO userObject, AuthorizationMemoryDAO authorizationObject){

        this.userObject = userObject;
        this.authorizationObject = authorizationObject;
    }

    /*public void clear() {
        userObject.clear();
    }*/

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
