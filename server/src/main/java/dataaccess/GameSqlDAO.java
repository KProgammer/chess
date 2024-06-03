package dataaccess;

import chess.ChessGame;
import model.Game;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameSqlDAO implements GameDAO{
    Map<Integer, Game> LIST_OF_GAMES = new HashMap<>();

    public GameSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void createGame(int gameID, String gameName) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, ChessGame) " +
                            "VALUES ('"+gameID+"', NULL, NULL, '"+gameName+", '"+new ChessGame()+"'")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public Game getGame(int gameID) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, ChessGame FROM authorization WHERE" +
                    "gameID = '"+gameID+"' LIMIT 1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
        return null;
    }

    @Override
    public Collection<Game> listGames() throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authorization")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
        return null;
    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor teamColor) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET teamColor = '"+teamColor+
                    "' WHERE gameID = '"+gameID+"'")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public void updateGame(int gameID, String newGamename) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET gameName = '"+newGamename+
                    "' WHERE gameID = '"+gameID+"'")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public void updateGame(int gameID, ChessGame newGame) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET ChessGame = '"+newGame+
                    "' WHERE gameID = '"+gameID+"'")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    @Override
    public int getGameID(String gameName) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID FROM authorization WHERE" +
                    "gameName = '"+gameName+"' LIMIT 1")) {
             var rs = preparedStatement.executeQuery();
             rs.next();
            }
        }
        return 0;
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM game")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
            }
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` SMALLINT(255),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `ChessGame` varchar(256),
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName),
              INDEX(ChessGame)
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
