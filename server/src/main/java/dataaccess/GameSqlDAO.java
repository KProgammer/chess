package dataaccess;

import chess.ChessGame;
import model.Game;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.client.HttpResponseException;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpVersion;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSqlDAO implements GameDAO{
    Map<Integer, Game> LIST_OF_GAMES = new HashMap<>();

    public GameSqlDAO() throws DataAccessException {
        configureDatabase();
    }
    public void example() throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT 1+1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }

    @Override
    public void createGame(int gameID, String gameName) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT 1+1")) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }

    @Override
    public Game getGame(int gameID){

    }

    @Override
    public Collection<Game> listGames(){

    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor teamColor){

    }

    @Override
    public void updateGame(int gameID, String newGamename){

    }

    @Override
    public void updateGame(int gameID, ChessGame newGame){

    }

    @Override
    public int getGameID(String gameName){

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
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT
              `gameID` SMALLINT(255),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName)
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
