package dataaccess;

import model.Authorization;

public class AuthorizationDAO {
    public Record createAuth(String authToken, String username){
        return new Authorization(authToken, username);
    }

    /*public Record getAuth(String authToken){

    }*/

    /*public void deleteAuth(String authToken){
        //
    }*/

    /*public void clear(){

    **/
}
