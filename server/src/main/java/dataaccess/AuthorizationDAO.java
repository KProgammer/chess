package dataaccess;

import model.Authorization;

import java.util.HashMap;
import java.util.Map;

public interface AuthorizationDAO {
    Map<String, Authorization> LIST_AUTHTOKEN_USER = new HashMap<>();

    String createAuth(String username);

    Authorization getAuth(String authToken);

    String getAuthToken(String username);

    void deleteAuth(String authToken);

    void clear();
}
