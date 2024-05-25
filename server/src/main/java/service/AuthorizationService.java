package service;

import dataaccess.AuthorizationDAO;

import java.util.ArrayList;
import java.util.Collection;

public class AuthorizationService {
    AuthorizationDAO authorizationObject = new AuthorizationDAO();

    public void clear(){
        authorizationObject.clear();
    }

    //login
    public Collection<String> login(String username, String password){


        if(authorizationObject.getAuth(username) == null){

        }

        return new ArrayList<>();
    }
    //logout
}