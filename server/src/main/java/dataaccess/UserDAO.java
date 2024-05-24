package dataaccess;

import model.Game;
import model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDAO {
    Map<String, User> ListOfUsers = new Map<>() {
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
        public User get(Object key) {
            return null;
        }

        @Override
        public User put(String key, User value) {
            return null;
        }

        @Override
        public User remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ? extends User> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return Set.of();
        }

        @Override
        public Collection<User> values() {
            return List.of();
        }

        @Override
        public Set<Entry<String, User>> entrySet() {
            return Set.of();
        }
    };

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
