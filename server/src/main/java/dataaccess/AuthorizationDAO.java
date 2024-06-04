package dataaccess;

import model.Authorization;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface AuthorizationDAO extends DAO {
    Map<String, Authorization> LIST_AUTHTOKEN_USER = new HashMap<>();

    String createAuth(String username) throws Exception;

    Authorization getAuth(String authToken) throws Exception;

    String getAuthToken(String username) throws Exception;

    void deleteAuth(String authToken) throws Exception;

    void clear() throws Exception;
}
