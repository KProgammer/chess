package dataaccess;

import model.Authorization;
import java.util.*;

public class AuthorizationDAO {

    Map<String, Authorization> ListAuthtokenUser = new HashMap<>();

    public String createAuth(String username){
        String authToken = UUID.randomUUID().toString();

        //Make sure that the authToken is unique.
        while(ListAuthtokenUser.get(authToken) != null) {
            authToken = UUID.randomUUID().toString();
        }
        Authorization newAuth = new Authorization(authToken, username);

        ListAuthtokenUser.put(authToken, newAuth);
        return authToken;
    }

    public Authorization getAuth(String authToken){
        return ListAuthtokenUser.get(authToken);
    }

    public void deleteAuth(String authToken){
        ListAuthtokenUser.remove(authToken);
    }

    public void clear(){
        ListAuthtokenUser.clear();
    }
}


