package dataaccess;

import model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface UserDAO {
    Map<String, User> LIST_OF_USERS = new HashMap<>();

    void createUser(String username, String password, String email) throws Exception;

    User getUser(String username) throws Exception;

    void clear() throws Exception;

    public String[] createStatement();

    default void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatement()) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
