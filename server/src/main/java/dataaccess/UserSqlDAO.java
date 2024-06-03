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
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,email);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public User getUser(String username) throws Exception{
        String password = null;
        String email = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT user, password, email FROM user WHERE username = ? LIMIT 1")) {
                preparedStatement.setString(1,username);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        password = rs.getString("password");
                        email = rs.getString("email");
                    }
                }
            }
        }
        return new User(username,password,email);
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM user")) {
                preparedStatement.executeUpdate();
            }
        }
    }

    private final String[] createStatements = {
            //`json` TEXT DEFAULT NULL,
            """
            CREATE TABLE IF NOT EXISTS  user (
              `id` int NOT NULL AUTO_INCREMENT
              `username` varchar(256),
              `password` varchar(256),
              `email` varchar(256),
              PRIMARY KEY (`id`),
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
