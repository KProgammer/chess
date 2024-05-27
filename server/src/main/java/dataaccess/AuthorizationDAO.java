package dataaccess;

import model.Authorization;

import java.util.HashMap;
import java.util.Map;

public interface AuthorizationDAO {
    Map<String, Authorization> ListAuthtokenUser = new HashMap<>();

    String createAuth(String username);

    Authorization getAuth(String authToken);

    void deleteAuth(String authToken);

    void clear();
}
