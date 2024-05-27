package dataaccess;

import model.User;

import java.util.*;

public class UserMemoryDAO implements UserDAO{

    @Override
    public void createUser(String username, String password, String email){
        ListOfUsers.put(username,new User(username, password, email));
    }

    @Override
    public User getUser(String username){
        return ListOfUsers.get(username);
    }

    @Override
    public void clear(){
        ListOfUsers.clear();
    }
}
