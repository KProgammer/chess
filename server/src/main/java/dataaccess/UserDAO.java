package dataaccess;

import model.User;

public class UserDAO {
    public User createUser(String username, String password, String email){
        return new User(username, password, email);
    }

    /*public User getUser(String username){

    }*/

    public void clear(){

    }
}
