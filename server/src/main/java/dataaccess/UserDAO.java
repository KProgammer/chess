package dataaccess;

import model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface UserDAO extends DAO {
    Map<String, User> LIST_OF_USERS = new HashMap<>();

    void createUser(String username, String password, String email) throws Exception;

    User getUser(String username) throws Exception;

    void clear() throws Exception;
}
