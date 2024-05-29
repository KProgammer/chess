package dataaccess;

import model.Authorization;
import java.util.*;

public class AuthorizationMemoryDAO implements AuthorizationDAO {

    @Override
    public String createAuth(String username){
        String authToken = UUID.randomUUID().toString();

        //Make sure that the authToken is unique.
        while(LIST_AUTHTOKEN_USER.get(authToken) != null) {
            authToken = UUID.randomUUID().toString();
        }
        Authorization newAuth = new Authorization(authToken, username);

        LIST_AUTHTOKEN_USER.put(authToken, newAuth);
        return authToken;
    }

    @Override
    public Authorization getAuth(String authToken){
        return LIST_AUTHTOKEN_USER.get(authToken);
    }

    @Override
    public String getAuthToken(String username){
        for(String token : LIST_AUTHTOKEN_USER.keySet()){
            if(LIST_AUTHTOKEN_USER.get(token).username() == username){
                return LIST_AUTHTOKEN_USER.get(token).authToken();
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken){
        LIST_AUTHTOKEN_USER.remove(authToken);
    }

    public void clear(){
        LIST_AUTHTOKEN_USER.clear();
    }
}


