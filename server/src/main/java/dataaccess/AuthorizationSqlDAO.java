package dataaccess;

import model.Authorization;

import java.sql.SQLException;
import java.util.UUID;

public class AuthorizationSqlDAO implements AuthorizationDAO {

    public AuthorizationSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public String createAuth(String username) throws Exception{
        String newAuthtoken = UUID.randomUUID().toString();

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO authorization (username, authToken) VALUES (?, ?)")) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2,newAuthtoken);
                preparedStatement.executeUpdate();
                // var rs = preparedStatement.executeUpdate();
                //rs.next();
            }
        }
        return newAuthtoken;
    }

    @Override
    public Authorization getAuth(String authToken) throws Exception {
        String username = null;
        String recauthToken = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username, authToken FROM authorization WHERE" +
                    "authToken = ? LIMIT 1")) {
                preparedStatement.setString(1,authToken);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        username = rs.getString("username");
                        recauthToken = rs.getString("authToken");
                    }
                }

            }
        }
        return new Authorization(username, recauthToken);
    }

    @Override
    public String getAuthToken(String username) throws Exception {
        String authToken = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM authorization WHERE username = ? LIMIT 1")) {
                preparedStatement.setString(1,username);
                try (var rs = preparedStatement.executeQuery()) {
                    while(rs.next()){
                        authToken = rs.getString("authToken");
                    }
                }
            }
        }
        return authToken;
    }

    @Override
    public void deleteAuth(String authToken) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authorization WHERE" +
                    "authToken = ? LIMIT 1")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authorization")) {
                preparedStatement.executeUpdate();
            }
        }
    }

    private final String[] createStatements = {
            //NOT NULL AUTO_INCREMENT
            """
            CREATE TABLE IF NOT EXISTS authorization (
              `id` INT NOT NULL AUTO_INCREMENT,
              `userName` varchar(256),
              `authToken` varchar(256),
              PRIMARY KEY (`id`),
              INDEX(username),
              INDEX(authToken)
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
