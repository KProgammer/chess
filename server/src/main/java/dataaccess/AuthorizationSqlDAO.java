package dataaccess;

import model.Authorization;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationSqlDAO implements AuthorizationDAO {

    AuthorizationSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public String createAuth(String username) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO  game")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public Authorization getAuth(String authToken){

    }

    @Override
    public String getAuthToken(String username){

    }

    @Override
    public void deleteAuth(String authToken){

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
              `id` int NOT NULL AUTO_INCREMENT
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
