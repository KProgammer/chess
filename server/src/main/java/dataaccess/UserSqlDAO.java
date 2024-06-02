package dataaccess;

import model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserSqlDAO implements UserDAO{
    Map<String, User> LIST_OF_USERS = new HashMap<>();

    public UserSqlDAO() throws DataAccessException{
        configureDatabase();
    }

    @Override
    public void createUser(String username, String password, String email) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) " +
                    "VALUES ('"+username+"', '"+password+", '"+email+"'")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public User getUser(String username) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT user, password, email FROM user WHERE" +
                    "username = '"+username+"' LIMIT 1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
        return null;
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DROP TABLE user")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `id` int NOT NULL AUTO_INCREMENT
              `username` varchar(256),
              `password` varchar(256),
              `email` varchar(256),
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`username`),
              INDEX(username),
              INDEX(password),
              INDEX(email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
