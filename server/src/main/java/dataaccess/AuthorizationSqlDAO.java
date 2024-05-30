package dataaccess;

import model.Authorization;

import java.sql.SQLException;
import java.util.UUID;

public class AuthorizationSqlDAO implements AuthorizationDAO {

    AuthorizationSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public String createAuth(String username) throws Exception{
        String newAuthtoken = UUID.randomUUID().toString();

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO authorization (id, username, authToken, json) " +
                    "VALUES ("+newAuthtoken+", "+username+", "+newAuthtoken+")")) {
                var rs = preparedStatement.executeQuery();
                 rs.next();
            }
        }
        return newAuthtoken;
    }

    @Override
    public Authorization getAuth(String authToken) throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username, authToken FROM authorization WHERE" +
                    "authToken = '"+authToken+"' LIMIT 1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
        return null;
    }

    @Override
    public String getAuthToken(String username) throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM authorization WHERE" +
                    "username = '"+username+"' LIMIT 1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authorization WHERE" +
                    "authToken = '"+authToken+"' LIMIT 1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DROP TABLE game")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authorization (
              `id` varchar(256) NOT NULL AUTO_INCREMENT
              `userName` varchar(256),
              `authToken` varchar(256),
              `json` TEXT DEFAULT NULL,
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
