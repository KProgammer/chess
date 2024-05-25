package dataaccess;

import model.Game;
import model.User;

import java.util.*;

public class UserDAO {
    Map<String, User> ListOfUsers = new HashMap<>();

    public void createUser(String username, String password, String email){
        ListOfUsers.put(username,new User(username, password, email));
    }

    public User getUser(String username){
        return ListOfUsers.get(username);
    }

    public void clear(){
        ListOfUsers.clear();
    }
}
