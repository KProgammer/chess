package dataaccess;

import model.User;

import java.util.HashMap;
import java.util.Map;

public interface UserDAO {
    Map<String, User> LIST_OF_USERS = new HashMap<>();

    void createUser(String username, String password, String email);

    User getUser(String username);

    void clear();
}
