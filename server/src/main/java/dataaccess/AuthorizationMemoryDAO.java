package dataaccess;

import model.Authorization;
import java.util.*;

public class AuthorizationMemoryDAO implements AuthorizationDAO {

    @Override
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

    @Override
    public Authorization getAuth(String authToken){
        return ListAuthtokenUser.get(authToken);
    }

    @Override
    public String getAuthToken(String username){
        for(String token : ListAuthtokenUser.keySet()){
            if(ListAuthtokenUser.get(token).username() == username){
                return ListAuthtokenUser.get(token).authToken();
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken){
        ListAuthtokenUser.remove(authToken);
    }

    public void clear(){
        ListAuthtokenUser.clear();
    }
}


