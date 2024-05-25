package dataaccess;

import model.Authorization;
import java.util.*;

public class AuthorizationDAO {

    Map<String, Authorization> ListAuthtokenUser = new Map<>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Authorization get(Object key) {
            return null;
        }

        @Override
        public Authorization put(String key, Authorization value) {
            return null;
        }

        @Override
        public Authorization remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends Authorization> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return Set.of();
        }

        @Override
        public Collection<Authorization> values() {
            return List.of();
        }

        @Override
        public Set<Entry<String, Authorization>> entrySet() {
            return Set.of();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    };

    public String createAuth(String username){
        String authToken = UUID.randomUUID().toString();

        //Make sure that the authToken is unique.
        while(ListAuthtokenUser.get(authToken) != null) {
            authToken = UUID.randomUUID().toString();
        }
        Authorization newAuth = new Authorization(authToken, username);

        ListAuthtokenUser.put(authToken, newAuth);
        return authToken;
    }

    public Authorization getAuth(String authToken){
        return ListAuthtokenUser.get(authToken);
    }

    public void deleteAuth(String authToken){
        ListAuthtokenUser.remove(authToken);
    }

    public void clear(){
        ListAuthtokenUser.clear();
    }
}


