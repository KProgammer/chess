package dataaccess;

import model.Authorization;
import java.util.*;

public class AuthorizationDAO {

    Map<String, Record> ListAuthtokenUser = new Map<>() {
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
        public Record get(Object key) {
            return null;
        }

        @Override
        public Record put(String key, Record value) {
            return null;
        }

        @Override
        public Record remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends Record> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return Set.of();
        }

        @Override
        public Collection<Record> values() {
            return List.of();
        }

        @Override
        public Set<Entry<String, Record>> entrySet() {
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

    public void createAuth(String username){
        String authToken = UUID.randomUUID().toString();

        //Make sure that the authToken is unique.
        while(ListAuthtokenUser.get(authToken) != null) {
            authToken = UUID.randomUUID().toString();
        }

        ListAuthtokenUser.put(authToken, new Authorization(authToken, username));
    }

    public Record getAuth(String authToken){
        return ListAuthtokenUser.get(authToken);
    }

    public void deleteAuth(String authToken){
        ListAuthtokenUser.remove(authToken);
    }

    public void clear(){
        ListAuthtokenUser.clear();
    }
}


