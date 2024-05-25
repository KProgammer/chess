package service;

import dataaccess.AuthorizationDAO;
import dataaccess.UserDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AuthorizationService {
    private final AuthorizationDAO authorizationObject;
    private final UserDAO userObject;
    //AuthorizationDAO authorizationObject = new AuthorizationDAO();
    //UserDAO userObject = new UserDAO();

    public AuthorizationService(AuthorizationDAO authorizationObject, UserDAO userObject){

        this.authorizationObject = authorizationObject;
        this.userObject = userObject;
    }

    public void clear(){
        authorizationObject.clear();
    }

    //login
    public Collection<String> login(String username, String password){
        ArrayList<String> result = new ArrayList<>();

        if(userObject.getUser(username) == null ||
                !Objects.equals(userObject.getUser(username).password(), password)){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        result.add("'"+username+"':");
        result.add("'"+authorizationObject.createAuth(username)+"':");
        return result;
    }

    //logout
    public Collection<String>    logout(String authToken){
        ArrayList<String> result = new ArrayList<>();

        if(authorizationObject.getAuth(authToken) == null){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        return result;
    }
}