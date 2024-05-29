package dataaccess;

import model.User;

public class UserMemoryDAO implements UserDAO{

    @Override
    public void createUser(String username, String password, String email){
        LIST_OF_USERS.put(username,new User(username, password, email));
    }

    @Override
    public User getUser(String username){
        return LIST_OF_USERS.get(username);
    }

    @Override
    public void clear(){
        LIST_OF_USERS.clear();
    }
}
